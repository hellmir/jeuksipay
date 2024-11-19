package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.SignInCommand;

public interface AuthenticationUseCase {
    AuthenticationResult signInMember(SignInCommand signInCommand);

    String issueNewAccessToken(String refreshToken);
}
