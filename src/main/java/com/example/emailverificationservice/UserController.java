package com.example.emailverificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:49357/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/email/signup")
    public ResponseEntity<String> signUp(@Validated @RequestBody UserDTO userDto) {
        // 회원가입 로직을 여기에 구현합니다.
        // userDto에는 클라이언트에서 전송한 사용자 정보가 담겨 있습니다.

        // 예를 들어, 사용자 정보를 데이터베이스에 저장하고 회원가입 성공 여부에 따라 응답을 보낼 수 있습니다.
        userRepository.save(userDto.toEntity());

        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다.");
    }
}
