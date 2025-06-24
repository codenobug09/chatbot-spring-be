package cntt.nckh.chatbot.repository;


import cntt.nckh.chatbot.entity.UsersOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEmailRepository extends JpaRepository<UsersOtp, Long> {
    boolean existsByEmail(String email);

    UsersOtp findByEmail(String email);
}
