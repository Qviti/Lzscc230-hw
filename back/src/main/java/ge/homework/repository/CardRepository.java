package ge.homework.repository;

import ge.homework.model.entity.Card;
import ge.homework.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUserAndCardNumberAndCardSecurityCode(User user, String number, String code);
}