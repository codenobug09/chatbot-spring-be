package cntt.nckh.chatbot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @NotNull(message = "Email ko được để trống")
    @Email(message = "Email phải để đúng định dạng")
    private String email;
}
