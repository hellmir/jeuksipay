package personal.jeuksipay.member.adapter.out.persistence.member;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.jeuksipay.member.domain.wrapper.Email;
import personal.jeuksipay.member.domain.wrapper.Phone;
import personal.jeuksipay.member.domain.wrapper.Username;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberJpaEntity, Long> {
    boolean existsByEmail(Email email);

    boolean existsByUsername(Username username);

    boolean existsByPhone(Phone phone);

    Optional<MemberJpaEntity> findByEmail(Email email);

    Optional<MemberJpaEntity> findByUsername(Username username);
}
