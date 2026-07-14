package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class UserRequestValidationTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 빈_회원가입_요청은_필수값_검증에_실패한다() throws Exception {
        SignupRequest request = objectMapper.readValue("{}", SignupRequest.class);

        Set<String> invalidFields = invalidFields(validator.validate(request));

        assertThat(invalidFields).contains(
                "name",
                "gender",
                "maleCount",
                "femaleCount",
                "birthYMD",
                "residence",
                "privacyAgreed",
                "purpose",
                "visitTime"
        );
    }

    @Test
    void 빈_체크인_요청은_필수값_검증에_실패한다() throws Exception {
        UserCheckInRequest request = objectMapper.readValue("{}", UserCheckInRequest.class);

        Set<String> invalidFields = invalidFields(validator.validate(request));

        assertThat(invalidFields).contains("userId", "purpose", "maleCount", "femaleCount", "visitTime");
    }

    @Test
    void 음수_동행인과_개인정보_미동의는_회원가입할_수_없다() throws Exception {
        SignupRequest request = objectMapper.readValue("""
                {
                  "name": "테스터",
                  "gender": "MAN",
                  "phone": "01000000000",
                  "maleCount": -1,
                  "femaleCount": 0,
                  "birthYMD": "2000-01-01",
                  "residence": "테스트",
                  "privacyAgreed": false,
                  "purpose": "테스트",
                  "visitTime": "2026-07-14T14:30:00"
                }
                """, SignupRequest.class);

        Set<String> invalidFields = invalidFields(validator.validate(request));

        assertThat(invalidFields).containsExactlyInAnyOrder("maleCount", "privacyAgreed");
    }

    private Set<String> invalidFields(Set<? extends ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }
}
