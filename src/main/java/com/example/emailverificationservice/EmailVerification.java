package com.example.emailverificationservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

@Service
public class EmailVerification {

    @Autowired
    private JavaMailSender mailSender;
    private final String username;
    private final String password;
    private final Properties props;
    private final Session session;

    public EmailVerification() {
        Dotenv dotenv = Dotenv.load();
        username = dotenv.get("GMAIL_USERNAME");
        password = dotenv.get("GMAIL_PASSWORD");

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }


    public String sendVerificationCode(String recipient) {
        String verificationCode = generateVerificationCode();
        String subject = "Connect Community 인증 번호 발송";
        String body = "인증 번호는 " + verificationCode + " 입니다.";

        sendEmail(recipient, subject, body);

        return verificationCode; // 생성된 인증 코드를 반환합니다.
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 랜덤 숫자 생성
        return String.valueOf(code);
    }

    private void sendEmail(String recipient, String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("이메일 전송 완료.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
