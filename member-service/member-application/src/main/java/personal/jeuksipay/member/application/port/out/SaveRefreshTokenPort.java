package personal.jeuksipay.member.application.port.out;

import personal.jeuksipay.member.domain.RefreshToken;

public interface SaveRefreshTokenPort {
    void saveRefreshToken(RefreshToken refreshToken);
}
