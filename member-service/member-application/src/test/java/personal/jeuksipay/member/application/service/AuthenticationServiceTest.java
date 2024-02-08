package personal.jeuksipay.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.signInCommand;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.port.out.SaveRefreshTokenPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationPort authenticationPort;

    @Mock
    private FindMemberPort findMemberPort;

    @Mock
    private SaveRefreshTokenPort saveRefreshTokenPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidator passwordValidator;

    @DisplayName("가입된 이메일 주소 또는 사용자 이름과 올바른 비밀번호를 전송하면 로그인을 할 수 있다.")
    @Test
    void signInMember() {
        // given
        signInCommand signInCommand = new signInCommand(EMAIL, PASSWORD1);
        Member member = MemberTestObjectFactory.createMember(
                ID_EXAMPLE, EMAIL, USERNAME, PASSWORD1, passwordEncoder,
                FULL_NAME, PHONE, List.of(ROLE_GENERAL_USER.toString())
        );

        when(findMemberPort.findMemberByEmailOrUsername(signInCommand.getEmailOrUsername())).thenReturn(member);
        when(authenticationPort.generateAccessToken(any())).thenReturn(TOKEN_VALUE1);
        when(authenticationPort.generateRefreshToken(any())).thenReturn(TOKEN_VALUE2);

        // when
        AuthenticationResult authenticationResult = authenticationService.signInMember(signInCommand);

        // then
        assertThat(authenticationResult.getRoleDescriptions()).isEqualTo(List.of(ROLE_GENERAL_USER.getDescription()));
        assertThat(authenticationResult.getLastLoggedInAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(authenticationResult.getAccessToken()).isEqualTo(TOKEN_VALUE1);
        assertThat(authenticationResult.getRefreshToken()).isEqualTo(TOKEN_VALUE2);
    }
}