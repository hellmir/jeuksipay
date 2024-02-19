package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.command.EmailUpdateCommand;

public interface UpdateMemberUseCase {
    void updateEmail(EmailUpdateCommand emailUpdateCommand);
}
