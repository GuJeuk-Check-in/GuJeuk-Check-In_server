package com.example.gujeuck_server.global.security.auth;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.pet.domain.PetUser;
import com.example.gujeuck_server.domain.pet.domain.enums.PrincipalType;
import com.example.gujeuck_server.domain.pet.domain.repository.PetUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final OrganRepository organRepository;
    private final PetUserRepository petUserRepository;

    @Override
    public UserDetails loadUserByUsername(String organName) {
        return loadUserByPrincipal(PrincipalType.ORGAN, organName);
    }

    public UserDetails loadUserByPrincipal(PrincipalType principalType, String principal) {
        if (principalType == PrincipalType.PET_USER) {
            return loadPetUser(principal);
        }

        Organ organ = organRepository.findByOrganName(principal)
                .orElseThrow(() -> new UsernameNotFoundException("Organ Not Found"));

        return new CustomUserDetails(organ);
    }

    private UserDetails loadPetUser(String principal) {
        Long petUserId = parsePetUserId(principal);

        PetUser petUser = petUserRepository.findById(petUserId)
                .orElseThrow(() -> new UsernameNotFoundException("Pet User Not Found"));

        return new PetUserDetails(petUser);
    }

    private Long parsePetUserId(String principal) {
        try {
            return Long.parseLong(principal);
        } catch (NumberFormatException exception) {
            throw new UsernameNotFoundException("Invalid Pet User Principal");
        }
    }
}
