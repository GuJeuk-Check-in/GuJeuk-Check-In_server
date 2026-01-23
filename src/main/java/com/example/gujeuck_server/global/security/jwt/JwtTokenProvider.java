package com.example.gujeuck_server.global.security.jwt;

import com.example.gujeuck_server.domain.organ.domain.enums.Client;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.organ.domain.RefreshToken;
import com.example.gujeuck_server.domain.organ.domain.repository.RefreshTokenRepository;
import com.example.gujeuck_server.domain.user.exception.ExpiredTokenException;
import com.example.gujeuck_server.domain.user.exception.InvalidTokenException;
import com.example.gujeuck_server.global.security.auth.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final OrganRepository organRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String CLAIM_TYPE = "type";
    private static final String ACCESS_TYPE = "access";
    private static final String REFRESH_TYPE = "refresh";
    private static final int MILLISECONDS = 2000;
    private static final long USER_VIEW_ACCESS_EXPIRATION = 86400L; // 24시간
    private static final long ADMIN_ACCESS_EXPIRATION = 7200L; // 2시간

    //access token 생성
    public String createAccessToken(String organName, Client client) {

        Date now = new Date();

        // Client 타입에 따라 만료시간 설정
        long expirationTime = (client == Client.USER_VIEW)
                ? USER_VIEW_ACCESS_EXPIRATION
                : ADMIN_ACCESS_EXPIRATION;

        return Jwts.builder()
                .setSubject(organName)
                .claim(CLAIM_TYPE, ACCESS_TYPE) // 액세스 토큰임을 나타냄
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expirationTime * MILLISECONDS)) // 토큰의 만료 시간 설정
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();

    }

    //refresh token 생성
    public String createRefreshToken(String organName, Client client) {

        Date now = new Date();

        String refreshToken = Jwts.builder()
                .claim(CLAIM_TYPE, REFRESH_TYPE)  //refresh 토큰임을 나타냄
                .setIssuedAt(now)
                .setExpiration(new java.sql.Timestamp(now.getTime() + jwtProperties.getRefreshExpiration() * MILLISECONDS))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();


        refreshTokenRepository.save(
                RefreshToken.builder()
                        .organName(organName)
                        .client(client.name())
                        .token(refreshToken)
                        .timeToLive((jwtProperties.getRefreshExpiration()))
                        .build()
        );

        return refreshToken;
    }

    // 토큰에 담겨 있는 userId로 SpringSecurity Authentication 정보를 반환 하는 메서드
    public Authentication getAuthentication(String token) {

        Claims claims = getClaims(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims getClaims(String token) {

        try {
            return Jwts
                    .parser() //JWT parser 생성
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException e) {
            throw  ExpiredTokenException.EXCEPTION;
        }
        catch (Exception E) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public TokenResponse receiveToken(String organName, Client client) {

        organRepository.findByOrganName(organName)
                .orElseThrow(() -> OrganNotFoundException.EXCEPTION);

        return TokenResponse.builder()
                .accessToken(createAccessToken(organName, client))
                .refreshToken(createRefreshToken(organName, client))
                .build();
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(jwtProperties.getHeader());

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getPrefix())
                && bearerToken.length() > jwtProperties.getPrefix().length() + 1) {
            return bearerToken.substring(7);
        }

        return null;
    }
}