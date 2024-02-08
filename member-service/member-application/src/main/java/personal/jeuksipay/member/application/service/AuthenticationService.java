package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.common.application.UseCase;
import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.signInCommand;
import personal.jeuksipay.member.application.port.in.usecase.AuthenticationUseCase;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RequiredArgsConstructor
@UseCase
public class AuthenticationService implements AuthenticationUseCase {
    private final AuthenticationPort authenticationPort;
    private final FindMemberPort findMemberPort;
    private final PasswordValidator passwordValidator;

    @Override
    @Transactional(isolation = READ_COMMITTED, timeout = 15)
    public AuthenticationResult signInMember(signInCommand signInCommand) {
        Member signedUpMember = findMemberPort.findMemberByEmailOrUsername(signInCommand.getEmailOrUsername());
        passwordValidator.validatePassword(signedUpMember.getPassword(), signInCommand.getPassword());
        String accessToken = issueAccessToken(signedUpMember);

        return AuthenticationResult.from(signedUpMember, accessToken);
    }

    private String issueAccessToken(Member member) {
        String accessToken
                = authenticationPort.generateAccessToken(String.valueOf(member.getId()), member.getRoles().toStrings());

        return accessToken;
    }
}
