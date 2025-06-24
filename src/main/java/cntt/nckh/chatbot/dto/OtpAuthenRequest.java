package cntt.nckh.chatbot.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpAuthenRequest {
    private String email;
    private String otp;
    private String otpId;
}
