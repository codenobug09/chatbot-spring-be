package cntt.nckh.chatbot.service;

import cntt.nckh.chatbot.repository.OpenAIRepository;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;

@Service
public class ChatService {
//    @Value("${openai.api.key}")
    @Autowired
    private OpenAIRepository repository;

//    private final String openAiApiKey = "";
    private final Map<String, String> cache = new HashMap<>();
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String getKey(){
        return repository.findById(1L).get().getApiKey();
    }

    public String getChatResponse(String message) {
        // Kiểm tra cache trước khi gọi API
        if (cache.containsKey(message)) {
            return cache.get(message);
        }
        // Gọi OpenAI API để lấy kết quả
        String response = callOpenAI(message);
        // Lưu kết quả vào cache để tối ưu hiệu suất
        cache.put(message, response);

        return response;
    }

    private String callOpenAI(String message) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(Map.of("role", "user", "content", message)));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        int maxRetries = 3; // Số lần thử lại tối đa
        int retryDelay = 2000; // 2 giây chờ trước khi thử lại

        for (int i = 0; i < maxRetries; i++) {
            try {
                ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, Map.class);
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    System.out.println("Lỗi 429: Đợi " + retryDelay / 1000 + " giây trước khi thử lại...");
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ignored) {}
                    retryDelay *= 2; // Tăng thời gian chờ mỗi lần (Exponential Backoff)
                } else {
                    return "Lỗi OpenAI API: " + e.getMessage();
                }
            }
        }
        return "Lỗi 429: Quá nhiều request, hãy thử lại sau!";
    }

    public String getAnswerFromOpenAi(String question, String context) {
        OpenAiService service = new OpenAiService(getKey(), Duration.ofSeconds(30));

        String prompt = "Dựa trên dữ liệu sau đây, hãy trả lời câu hỏi:\n" + question + "\n\nDữ liệu: " + context;

        System.out.println("Gửi lên OpenAI: " + prompt); // Debug

        ChatMessage message = new ChatMessage("user", prompt);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4")
                .messages(Collections.singletonList(message))
                .maxTokens(200)
                .temperature(0.7)
                .build();

        ChatCompletionResult result = service.createChatCompletion(request);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
