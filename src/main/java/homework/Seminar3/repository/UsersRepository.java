package homework.Seminar3.repository;

import homework.Seminar3.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByName(String name);
}
