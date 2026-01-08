//package com.example.gujeuck_server.domain.user.service;
//
//import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
//import com.example.gujeuck_server.domain.user.presentation.dto.request.LoginRequest;
//import com.example.gujeuck_server.domain.user.domain.User;
//import com.example.gujeuck_server.domain.user.domain.enums.Age;
//import com.example.gujeuck_server.domain.user.domain.enums.Gender;
//import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@SpringBootTest
//class LoginServiceTest {
//
//    @Autowired
//    private LoginUserService loginService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private LogRepository logRepository;
//
//    @BeforeEach
//    void setup() {
//        logRepository.deleteAll(); // 테스트 전 로그 초기화
//        userRepository.deleteAll(); // 테스트 전 유저 초기화
//
//        userRepository.save(User.builder()
//                .userId("박태수0504")
//                .name("박태수")
//                .phone("010-1234-5678")
//                .privacyAgreed(true)
//                .gender(Gender.MAN)
//                .residence("전주")
//                .age(Age.AGE_17_19)
//                .birthYMD("2008-05-04")
//                .count(1)
//                .build());
//    }
//
////    @Test
////    void 로그인_요청_성공하면_로그가_저장된다() {
////        // given
////        LoginRequest request = new LoginRequest("박태수0504", "게임", List.of());
////
////        // when
////        loginService.login(request);
////
////        // then
////        long count = logRepository.count();
////        assertThat(count).isEqualTo(1);
////    }
//
//    @Test
//    void 동시에_두번_로그인_요청시_하나만_성공한다() throws Exception {
//        // given
//        LoginRequest request = new LoginRequest("박태수0504", "게임", List.of());
//
//        // when
//        Thread thread1 = new Thread(() -> loginService.login(request));
//        Thread thread2 = new Thread(() -> loginService.login(request));
//        Thread thread3 = new Thread(() -> loginService.login(request));
//        Thread thread4 = new Thread(() -> loginService.login(request));
//        Thread thread5 = new Thread(() -> loginService.login(request));
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();
//
//        thread1.join();
//        thread2.join();
//        thread3.join();
//        thread4.join();
//        thread5.join();
//
//        // then
//        long count = logRepository.count();
//        assertThat(count).isEqualTo(1); // 중복 로그 저장 방지됨
//    }
//
//}

