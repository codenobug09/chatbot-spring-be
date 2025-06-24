package cntt.nckh.chatbot.service;

import cntt.nckh.chatbot.dto.EmailRequest;
import cntt.nckh.chatbot.dto.OtpAuthenRequest;
import cntt.nckh.chatbot.entity.Otp;
import cntt.nckh.chatbot.entity.UsersOtp;
import cntt.nckh.chatbot.repository.OtpRepository;
import cntt.nckh.chatbot.repository.UserEmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserEmailRepository mailRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserEmailRepository userEmailRepository;

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo OTP 6 chữ số
        return String.valueOf(otp);
    }

    public void sendOtp(EmailRequest email) throws Exception {
        if(mailRepository.existsByEmail(email.getEmail())) {
            String otp = generateOtp();

            Otp otpObj = Otp.builder()
                    .createdAt(LocalDateTime.now())
                    .expiredAt(LocalDateTime.now().plusMinutes(7))
                    .user(mailRepository.findByEmail(email.getEmail()))
                    .otp(Long.valueOf(otp)).build();
            otpRepository.save(otpObj);

            // Gửi email
            emailService.sendOtpEmail(email, otp);
        }
        else {
            throw new Exception("Ko có mail trong db");
        }
    }

    public void checkOtp(OtpAuthenRequest otpAuthenRequest)  throws Exception{
        if(!otpRepository.existsByOtp(Long.valueOf(otpAuthenRequest.getOtp()))){
            throw new Exception("Ko có otp trong db");
        }

        List<String> listOtp = otpRepository.findValidOtps();
        if(!listOtp.contains(otpAuthenRequest.getOtp())){
            throw new Exception("Otp đã hết hạn! Vui lòng gửi lại otp");
        }

        if(!mailRepository.existsByEmail(otpAuthenRequest.getEmail())) {
            throw new Exception("Ko có mail trong db");
        }

        if(!otpAuthenRequest.getOtp().equals(otpRepository.findOtpById(Long.valueOf(otpAuthenRequest.getOtpId())))){
            throw new Exception("Id của otp ko trùng OTP");
        }

        Optional<Otp> otpOptional = otpRepository.findById(Long.valueOf(otpAuthenRequest.getOtpId()));
        if(otpOptional.isPresent()){
            if(!otpOptional.get().getUser().getId().equals(userEmailRepository.findByEmail(otpAuthenRequest.getEmail()).getId())){
                throw new Exception("Otp k phải của người dùng");
            }
        }


    }
}

