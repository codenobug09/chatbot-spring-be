package cntt.nckh.chatbot.controller;

import cntt.nckh.chatbot.dto.ApiResponse;
import cntt.nckh.chatbot.dto.EmailRequest;
import cntt.nckh.chatbot.dto.OtpAuthenRequest;
import cntt.nckh.chatbot.dto.OtpResponse;
import cntt.nckh.chatbot.repository.OtpRepository;
import cntt.nckh.chatbot.repository.UserEmailRepository;
import cntt.nckh.chatbot.service.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Otp", description = "Send Otp")
public class EmailController {

    @Autowired
    private OtpService otpService;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private UserEmailRepository userEmailRepository;

    @PostMapping("/resend-otp")
    @Operation(summary = "Gửi Otp")
    public ResponseEntity<ApiResponse<OtpResponse>> sendOtp(@Valid @RequestBody EmailRequest email) throws Exception{
        otpService.sendOtp(email);
        return ResponseEntity.ok(new ApiResponse<>("0", "Id của otp", new OtpResponse(otpRepository.findAllByUserId(userEmailRepository.findByEmail(email.getEmail()).getId()).getLast().getId())));
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Nhập Otp")
    public ResponseEntity<ApiResponse<String>> checkOtp(@Valid @RequestBody OtpAuthenRequest otpAuthenRequest) throws Exception{
        otpService.checkOtp(otpAuthenRequest);
        return ResponseEntity.ok(new ApiResponse<>("0", "Xác thực OTP thành công", ""));
    }


}

