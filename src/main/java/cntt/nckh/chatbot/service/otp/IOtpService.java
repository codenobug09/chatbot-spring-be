package cntt.nckh.chatbot.service.otp;

import cntt.nckh.chatbot.dto.EmailRequest;
import cntt.nckh.chatbot.dto.OtpAuthenRequest;

public interface IOtpService {
    void sendOtpEmail(EmailRequest email, String otp);
    void sendOtp(EmailRequest email) throws Exception;
    void checkOtp(OtpAuthenRequest otpAuthenRequest)  throws Exception;
}
