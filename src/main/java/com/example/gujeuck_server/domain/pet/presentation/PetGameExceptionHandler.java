package com.example.gujeuck_server.domain.pet.presentation;

import com.example.gujeuck_server.domain.pet.exception.InvalidPetGameLoginInputException;
import com.example.gujeuck_server.domain.pet.presentation.dto.response.PetGameLoginErrorResponse;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = PetGameAuthController.class)
public class PetGameExceptionHandler {
    private static final String INVALID_INPUT_MESSAGE = "이름과 전화번호를 올바르게 입력해주세요.";
    private static final String USER_NOT_FOUND_MESSAGE = "회원 목록 및 방문 기록에서 찾을 수 없습니다.";
    private static final String SERVER_ERROR_MESSAGE = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<PetGameLoginErrorResponse> handleUserNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(PetGameLoginErrorResponse.of("USER_NOT_FOUND", USER_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler({
            InvalidPetGameLoginInputException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<PetGameLoginErrorResponse> handleInvalidInput() {
        return ResponseEntity.badRequest()
                .body(PetGameLoginErrorResponse.of("INVALID_INPUT", INVALID_INPUT_MESSAGE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PetGameLoginErrorResponse> handleServerError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(PetGameLoginErrorResponse.of("SERVER_ERROR", SERVER_ERROR_MESSAGE));
    }
}
