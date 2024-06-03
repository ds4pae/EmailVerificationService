package com.example.emailverificationservice;

import org.springframework.web.bind.annotation.CrossOrigin;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@RestController
@CrossOrigin(origins = "http://localhost:49357/")
public class EmailVerificationController {
    private String savedVerificationCode = "";
    @Autowired
    private EmailVerification emailVerification;

    @PostMapping("/api/email/sendCode")
    public ResponseEntity<?> sendVerificationCode(@RequestParam("email") String email) {
        System.out.println("***   " + email + "   ***");
        // 이메일 전송 로직
        savedVerificationCode = emailVerification.sendVerificationCode(email);

        return ResponseEntity.ok("Verification code sent successfully.");
    }

    @PostMapping("/api/email/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestParam("verificationCode") String verificationCode) {
        if (verificationCode.equals(savedVerificationCode)) {
            return ResponseEntity.ok("Verification successful.");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification code.");
        }
    }


}
