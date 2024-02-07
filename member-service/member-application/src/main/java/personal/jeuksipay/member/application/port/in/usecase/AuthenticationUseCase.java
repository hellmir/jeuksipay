package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.signInCommand;

public interface AuthenticationUseCase {
    AuthenticationResult signInMember(signInCommand signInCommand);
}
