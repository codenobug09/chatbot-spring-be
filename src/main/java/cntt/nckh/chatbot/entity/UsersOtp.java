package cntt.nckh.chatbot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Otp> otps;
}
