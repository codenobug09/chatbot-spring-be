package cntt.nckh.chatbot.repository;

import cntt.nckh.chatbot.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    boolean existsByOtp(Long otp);

    @Query("SELECT o.otp FROM Otp o WHERE o.expiredAt > CURRENT_TIMESTAMP")
    List<String> findValidOtps();

    @Query("SELECT o FROM Otp o WHERE o.user.id = :id")
    List<Otp> findAllByUserId(@Param("id") Long id);

    @Query("SELECT o.otp FROM Otp o WHERE o.id = :id")
    String findOtpById(Long id);

}
