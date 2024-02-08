package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.common.application.UseCase;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.application.port.in.mapper.MemberCommandToDomainMapper;
import personal.jeuksipay.member.application.port.in.usecase.SignUpUseCase;
import personal.jeuksipay.member.application.port.out.SignUpPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

@RequiredArgsConstructor
@UseCase
@Transactional(isolation = SERIALIZABLE, timeout = 20)
public class SignUpService implements SignUpUseCase {
    private final SignUpPort signUpPort;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member createMember(SignUpCommand signUpCommand) {
        Member member = MemberCommandToDomainMapper.mapToDomainEntity(signUpCommand, passwordEncoder);
        passwordValidator.validatePassword(member.getPassword(), signUpCommand.getPasswordConfirm());

        signUpPort.saveMember(member);

        return member;
    }
}
