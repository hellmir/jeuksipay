package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import lombok.RequiredArgsConstructor;
import personal.jeuksipay.common.adapter.out.PersistenceAdapter;
import personal.jeuksipay.member.adapter.out.mapper.RefreshTokenJpaEntityToDomainMapper;
import personal.jeuksipay.member.application.port.out.FindRefreshTokenPort;
import personal.jeuksipay.member.application.port.out.SaveRefreshTokenPort;
import personal.jeuksipay.member.domain.RefreshToken;

@PersistenceAdapter
@RequiredArgsConstructor
public class RefreshTokenPersistenceAdapter implements SaveRefreshTokenPort, FindRefreshTokenPort {
    private final RefreshTokenRepository refreshTokenRepository;

    static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "유효하지 않은 리프레시 토큰입니다. 전송된 토큰: ";

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        RefreshTokenJpaEntity refreshTokenJpaEntity = RefreshTokenJpaEntity.from(refreshToken);
        refreshTokenRepository.save(refreshTokenJpaEntity);
    }

    @Override
    public RefreshToken findRefreshToken(String refreshTokenValue) {
        RefreshTokenJpaEntity refreshTokenJpaEntity = refreshTokenRepository.findByTokenValue(refreshTokenValue)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_TOKEN_EXCEPTION_MESSAGE + refreshTokenValue));

        return RefreshTokenJpaEntityToDomainMapper.mapToDomainEntity(refreshTokenJpaEntity);
    }
}
