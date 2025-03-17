package cntt.nckh.chatbot.controller;

import cntt.nckh.chatbot.repository.OpenAIRepository;
import cntt.nckh.chatbot.service.ChatService;
import cntt.nckh.chatbot.service.JsonDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/{api_prefix}/chat")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ChatController {
//    private final ChatService chatService;
//
//    @PostMapping("/test")
//    public ResponseEntity<String> chat(@RequestBody ChatRequest chatRequest) {
//        String response = chatService.getChatResponse(chatRequest.getMessage());
//        return ResponseEntity.ok(response);
//    }
    @Autowired
    private OpenAIRepository repository;
    @Autowired
    private JsonDataService jsonDataService;

    @Autowired
    private ChatService chatService;

    @PostMapping("/test")
    public Map<String, String> askQuestion(@RequestBody Map<String, String> request) {
        String question = request.get("message");
        String context = jsonDataService.searchAnswer(question); // Tìm dữ liệu trong JSON

//      System.out.println("Dữ liệu tìm được: " + context); // Debug
        if (context.equals("Xin lỗi, tôi không có thông tin về câu hỏi này.")) {
            return Map.of("response", context);
        }
        String answer = chatService.getAnswerFromOpenAi(question, context);
        return Map.of("response", answer);
    }

//    @GetMapping("/get/{id}")
//    public String getById(@PathVariable Long id){
//        return repository.findById(id).get().getApiKey();
//    }
}
