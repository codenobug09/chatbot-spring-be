package cntt.nckh.chatbot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import lombok.Data;


@Data
public class EmailRequest {
    @NotNull(message = "Email ko được để trống")
    @Email(message = "Email phải để đúng định dạng")
    private String email;
}
