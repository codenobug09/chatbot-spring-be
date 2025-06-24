package cntt.nckh.chatbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(name = "OTP")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long otp;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersOtp user;

}
