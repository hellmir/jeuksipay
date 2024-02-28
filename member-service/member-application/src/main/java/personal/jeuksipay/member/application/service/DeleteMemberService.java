package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.common.application.UseCase;
import personal.jeuksipay.member.application.port.in.command.MemberDeleteCommand;
import personal.jeuksipay.member.application.port.in.usecase.DeleteMemberUseCase;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.DeleteMemberPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_COMMITTED, timeout = 20)
public class DeleteMemberService implements DeleteMemberUseCase {
    private final DeleteMemberPort deleteMemberPort;
    private final FindMemberPort findMemberPort;
    private final AuthenticationPort authenticationPort;
    private final PasswordValidator passwordValidator;

    @Override
    public void deleteMember(MemberDeleteCommand memberDeleteCommand) {
        Long memberId = Long.parseLong(authenticationPort.parseMemberId(memberDeleteCommand.getAccessToken()));
        Member member = findMemberPort.findMemberById(memberId);

        passwordValidator.validatePassword(member.getPassword(), memberDeleteCommand.getPassword());

        deleteMemberPort.deleteMember(memberId);
    }
}
