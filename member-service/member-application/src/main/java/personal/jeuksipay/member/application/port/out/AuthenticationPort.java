package personal.jeuksipay.member.application.port.out;

import org.springframework.security.core.Authentication;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.RefreshToken;

public interface AuthenticationPort {
    String generateAccessToken(Member member);

    String generateRefreshToken(Member member);

    String generateNewAccessToken(RefreshToken refreshToken);

    Authentication getAuthentication(String token);

    String parseMemberId(String accessToken);

    boolean validateToken(String accessToken);
}
