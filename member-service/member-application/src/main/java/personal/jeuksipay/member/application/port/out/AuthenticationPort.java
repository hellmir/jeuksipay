package personal.jeuksipay.member.application.port.out;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface AuthenticationPort {
    String generateAccessToken(String memberId, List<String> roles);

    Authentication getAuthentication(String token);

    String parseMemberId(String accessToken);

    boolean validateToken(String accessToken);
}
