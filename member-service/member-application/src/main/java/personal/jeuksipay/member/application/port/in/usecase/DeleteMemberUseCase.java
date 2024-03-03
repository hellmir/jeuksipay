package personal.jeuksipay.member.application.port.in.usecase;

import personal.jeuksipay.member.application.port.in.command.MemberDeleteCommand;

public interface DeleteMemberUseCase {
    void deleteMember(MemberDeleteCommand memberDeleteCommand);
}
