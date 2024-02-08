package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
}
