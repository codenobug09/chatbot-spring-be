package cntt.nckh.chatbot.service;

import cntt.nckh.chatbot.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(EmailRequest email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getEmail());
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + ". It will expire in 5 minutes.");
        mailSender.send(message);
    }
}
