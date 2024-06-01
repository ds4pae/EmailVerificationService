package com.example.emailverificationservice;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailVerificationController {

    @Autowired
    private EmailVerification emailVerification;

    @GetMapping("/")
    public String showVerificationForm(Model model) {
        model.addAttribute("email", new EmailVerificationDTO());
        return "verificationForm";
    }

    @PostMapping("/sendCode")
    public String sendVerificationCode(@ModelAttribute("email") EmailVerificationDTO emailVerificationDTO, RedirectAttributes redirectAttributes, HttpSession session) {
        String verificationCode = emailVerification.sendVerificationCode(emailVerificationDTO.getEmail());
        session.setAttribute("verificationCode", verificationCode); // 생성된 인증 코드를 세션에 저장
        redirectAttributes.addFlashAttribute("verificationCodeSent", true); // 인증 코드가 전송되었음을 알림
        return "redirect:/verificationCodeSent";
    }


    @GetMapping("/verificationCodeSent")
    public String verificationCodeSentPage(@ModelAttribute("verificationCodeSent") Boolean verificationCodeSent, Model model) {
        if (verificationCodeSent != null && verificationCodeSent) {
            return "verificationCodeSent";
        } else {
            model.addAttribute("errorMessage", "Verification code not sent.");
            return "error";
        }
    }

    @PostMapping("/verifyCode")
    public String verifyCode(@RequestParam("verificationCode") String verificationCode, HttpSession session) {
        String savedVerificationCode = (String) session.getAttribute("verificationCode");

        if (verificationCode.equals(savedVerificationCode)) {
            // 인증 성공
            return "verificationSuccess"; // 인증 성공 페이지로 이동
        } else {
            // 인증 실패
            return "verificationCodeSent"; // 인증 코드 다시 입력 페이지로 이동
        }
    }



}
