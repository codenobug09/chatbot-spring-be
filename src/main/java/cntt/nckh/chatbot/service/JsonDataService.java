package cntt.nckh.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Map;

@Service
public class JsonDataService {
    private Map<String, String> chatbotData;
    @Autowired
    private  ChatService chatService;

    public JsonDataService() {
        loadData();
    }

    private void loadData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
            chatbotData = objectMapper.readValue(inputStream, Map.class);
            // Debug: In ra dữ liệu đã load
            System.out.println("Dữ liệu đã nạp: " + chatbotData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String searchAnswer(String question) {
        String normalizedQuestion = question.toLowerCase().trim();

        // Kiểm tra nếu câu hỏi có trong dữ liệu
        if (chatbotData.containsKey(normalizedQuestion)) {
            return chatbotData.get(normalizedQuestion);
        }
        // Nếu không tìm thấy câu trả lời chính xác, thử tìm kiếm gần đúng
        for (Map.Entry<String, String> entry : chatbotData.entrySet()) {
            if (normalizedQuestion.contains(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return "Toi ko co cau trl cho cau hoi cua b";
//        return chatService.getChatResponse(question);

    }
}
