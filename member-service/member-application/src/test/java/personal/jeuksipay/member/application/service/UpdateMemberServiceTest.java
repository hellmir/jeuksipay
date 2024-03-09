package personal.jeuksipay.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.EmailUpdateCommand;
import personal.jeuksipay.member.application.port.in.command.PasswordUpdateCommand;
import personal.jeuksipay.member.application.port.in.command.PhoneUpdateCommand;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.port.out.UpdateMemberPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ExtendWith(MockitoExtension.class)
class UpdateMemberServiceTest {
    @InjectMocks
    private UpdateMemberService updateMemberService;

    @Mock
    private UpdateMemberPort updateMemberPort;

    @Mock
    private AuthenticationPort authenticationPort;

    @Mock
    private FindMemberPort findMemberPort;

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원의 주소를 변경할 수 있다.")
    @Test
    void updateAddress() {
        // given
        AddressCommand addressCommand = AddressCommand.builder()
                .city(CITY)
                .street(STREET)
                .zipcode(ZIPCODE)
                .detailedAddress(DETAILED_ADDRESS)
                .accessToken(TOKEN_VALUE1)
                .build();

        Member member = mock(Member.class);
        when(authenticationPort.parseMemberId(any())).thenReturn(ID_EXAMPLE);
        when(findMemberPort.findMemberById(any())).thenReturn(member);

        // when, then
        updateMemberService.updateAddress(addressCommand);
    }

    @DisplayName("회원의 이메일 주소를 변경할 수 있다.")
    @Test
    void updateEmail() {
        // given
        EmailUpdateCommand emailUpdateCommand = new EmailUpdateCommand(PASSWORD1, TOKEN_VALUE1, EMAIL);

        Member member = mock(Member.class);
        when(authenticationPort.parseMemberId(any())).thenReturn(ID_EXAMPLE);
        when(findMemberPort.findMemberById(any())).thenReturn(member);

        // when, then
        updateMemberService.updateEmail(emailUpdateCommand);
    }

    @DisplayName("회원의 전화번호를 변경할 수 있다.")
    @Test
    void updatePhone() {
        // given
        PhoneUpdateCommand phoneUpdateCommand = new PhoneUpdateCommand(PASSWORD1, TOKEN_VALUE1, EMAIL);

        Member member = mock(Member.class);
        when(authenticationPort.parseMemberId(any())).thenReturn(ID_EXAMPLE);
        when(findMemberPort.findMemberById(any())).thenReturn(member);

        // when, then
        updateMemberService.updatePhone(phoneUpdateCommand);
    }

    @DisplayName("회원의 비밀번호를 변경할 수 있다.")
    @Test
    void updatePassword() {
        // given
        PasswordUpdateCommand passwordUpdateCommand
                = new PasswordUpdateCommand(PASSWORD1, TOKEN_VALUE1, PASSWORD2, PASSWORD2);

        Member member = mock(Member.class);
        when(authenticationPort.parseMemberId(any())).thenReturn(ID_EXAMPLE);
        when(findMemberPort.findMemberById(any())).thenReturn(member);

        // when, then
        updateMemberService.updatePassword(passwordUpdateCommand);
    }
}
