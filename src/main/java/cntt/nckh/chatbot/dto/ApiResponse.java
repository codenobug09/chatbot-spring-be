package cntt.nckh.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String message;
    private T result;
}
