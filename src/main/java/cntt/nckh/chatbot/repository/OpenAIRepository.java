package cntt.nckh.chatbot.repository;

import cntt.nckh.chatbot.entity.ApiKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OpenAIRepository extends JpaRepository<ApiKey, Long> {
}
