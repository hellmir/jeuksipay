package personal.jeuksipay.member.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import personal.jeuksipay.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class AuthenticationResult {
    private final Long memberId;
    private final List<String> roleDescriptions;
    private final LocalDateTime lastLoggedInAt;
    private final String accessToken;
    private final String refreshToken;

    public static AuthenticationResult from(Member member, String accessToken, String refreshToken) {
        return AuthenticationResult.builder()
                .memberId(member.getId())
                .roleDescriptions(member.getRoles().toDescriptions())
                .lastLoggedInAt(member.getLastLoggedInAt())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
