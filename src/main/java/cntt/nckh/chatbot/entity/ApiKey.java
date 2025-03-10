package cntt.nckh.chatbot.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "ApiKey")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiKey {
    @Id
    private Long id;
    private String apiKey;
}
