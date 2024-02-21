package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.common.application.UseCase;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.EmailUpdateCommand;
import personal.jeuksipay.member.application.port.in.mapper.MemberCommandToDomainMapper;
import personal.jeuksipay.member.application.port.in.usecase.UpdateMemberUseCase;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.port.out.UpdateMemberPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = READ_COMMITTED, timeout = 10)
public class UpdateMemberService implements UpdateMemberUseCase {
    private final AuthenticationPort authenticationPort;
    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final PasswordValidator passwordValidator;


    @Override
    public void updateAddress(AddressCommand addressCommand) {
        String memberId = authenticationPort.parseMemberId(addressCommand.getAccessToken());
        Member member = findMemberPort.findMemberById(Long.parseLong(memberId));
        Address address = MemberCommandToDomainMapper.mapToAddress(addressCommand);

        member.updateAddress(address);
        updateMemberPort.updateMember(member);
    }

    @Override
    public void updateEmail(EmailUpdateCommand emailUpdateCommand) {
        String memberId = authenticationPort.parseMemberId(emailUpdateCommand.getAccessToken());
        Member member = findMemberPort.findMemberById(Long.parseLong(memberId));

        passwordValidator.validatePassword(member.getPassword(), emailUpdateCommand.getPassword());

        findMemberPort.checkDuplicateEmail(emailUpdateCommand.getEmail());

        member.updateEmail(emailUpdateCommand.getEmail());

        updateMemberPort.updateMember(member);
    }
}
