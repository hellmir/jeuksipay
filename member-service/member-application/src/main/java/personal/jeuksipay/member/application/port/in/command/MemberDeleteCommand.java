package personal.jeuksipay.member.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDeleteCommand {
    private final String password;
    private final String accessToken;
}
