package personal.jeuksipay.member.adapter.out.mapper;

import personal.jeuksipay.member.adapter.out.persistence.refreshtoken.RefreshTokenJpaEntity;
import personal.jeuksipay.member.domain.RefreshToken;

import java.util.List;

public class RefreshTokenJpaEntityToDomainMapper {
    public static RefreshToken mapToDomainEntity(RefreshTokenJpaEntity refreshTokenJpaEntity) {
        String memberId = refreshTokenJpaEntity.getMemberId();
        List<String> roles = refreshTokenJpaEntity.getRoles();
        String tokenValue = refreshTokenJpaEntity.getTokenValue();

        return RefreshToken.of(memberId, roles, tokenValue);
    }
}
