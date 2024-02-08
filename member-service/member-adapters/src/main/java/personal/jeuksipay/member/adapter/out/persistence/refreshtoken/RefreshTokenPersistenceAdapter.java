package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import lombok.RequiredArgsConstructor;
import personal.jeuksipay.common.adapter.out.PersistenceAdapter;
import personal.jeuksipay.member.application.port.out.FindRefreshTokenPort;
import personal.jeuksipay.member.application.port.out.SaveRefreshTokenPort;
import personal.jeuksipay.member.domain.RefreshToken;

@PersistenceAdapter
@RequiredArgsConstructor
public class RefreshTokenPersistenceAdapter implements SaveRefreshTokenPort, FindRefreshTokenPort {
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        RefreshTokenJpaEntity refreshTokenJpaEntity = RefreshTokenJpaEntity.from(refreshToken);
        refreshTokenRepository.save(refreshTokenJpaEntity);
    }
}
