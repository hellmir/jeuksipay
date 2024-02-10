package personal.jeuksipay.member.application.port.out;

import personal.jeuksipay.member.domain.RefreshToken;

public interface FindRefreshTokenPort {
    RefreshToken findRefreshToken(String refreshTokenValue);
}
