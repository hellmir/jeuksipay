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
    private List<String> roleDescriptions;
    private LocalDateTime lastLoggedInAt;
    private String accessToken;
    private String refreshToken;

    public static AuthenticationResult from(Member member, String accessToken, String refreshToken) {
        return AuthenticationResult.builder()
                .roleDescriptions(member.getRoles().toDescriptions())
                .lastLoggedInAt(member.getLastLoggedInAt())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
