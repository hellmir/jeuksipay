package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.domain.Member;

public interface SignUpUseCase {
    Member createMember(SignUpCommand signUpCommand);
}
