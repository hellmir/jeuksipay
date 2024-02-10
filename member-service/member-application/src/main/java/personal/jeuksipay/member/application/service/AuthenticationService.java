package personal.jeuksipay.member.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.common.application.UseCase;
import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.signInCommand;
import personal.jeuksipay.member.application.port.in.usecase.AuthenticationUseCase;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.port.out.FindRefreshTokenPort;
import personal.jeuksipay.member.application.port.out.SaveRefreshTokenPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.RefreshToken;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RequiredArgsConstructor
@UseCase
public class AuthenticationService implements AuthenticationUseCase {
    private final AuthenticationPort authenticationPort;
    private final FindMemberPort findMemberPort;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final FindRefreshTokenPort findRefreshTokenPort;
    private final PasswordValidator passwordValidator;

    @Override
    @Transactional(isolation = READ_COMMITTED, timeout = 15)
    public AuthenticationResult signInMember(signInCommand signInCommand) {
        Member signedUpMember = findMemberPort.findMemberByEmailOrUsername(signInCommand.getEmailOrUsername());
        passwordValidator.validatePassword(signedUpMember.getPassword(), signInCommand.getPassword());

        String accessTokenValue = authenticationPort.generateAccessToken(signedUpMember);
        String refreshTokenValue = issueRefreshToken(signedUpMember);

        return AuthenticationResult.from(signedUpMember, accessTokenValue, refreshTokenValue);
    }

    @Override
    public String issueNewAccessToken(String refreshTokenValue) {
        boolean tokenIsValid = authenticationPort.validateToken(refreshTokenValue);
        if (tokenIsValid) {
            RefreshToken refreshToken = findRefreshTokenPort.findRefreshToken(refreshTokenValue);
            return authenticationPort.generateNewAccessToken(refreshToken);
        }

        return null;
    }

    private String issueRefreshToken(Member member) {
        String refreshToken = authenticationPort.generateRefreshToken(member);
        saveRefreshTokenPort.saveRefreshToken(
                RefreshToken.of(member.getId().toString(), member.getRoles().toStrings(), refreshToken)
        );

        return refreshToken;
    }
}
