package personal.jeuksipay.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import personal.jeuksipay.member.application.port.in.command.MemberDeleteCommand;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.application.port.out.DeleteMemberPort;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.testutil.MemberTestConstant.PASSWORD1;
import static personal.jeuksipay.member.testutil.MemberTestConstant.TOKEN_VALUE1;

@ExtendWith(MockitoExtension.class)
class DeleteMemberServiceTest {
    @InjectMocks
    private DeleteMemberService deleteMemberService;

    @Mock
    private DeleteMemberPort deleteMemberPort;

    @Mock
    private AuthenticationPort authenticationPort;

    @Mock
    private FindMemberPort findMemberPort;

    @Mock
    private PasswordValidator passwordValidator;

    @DisplayName("비밀번호 인증을 통해 회원을 탈퇴할 수 있다.")
    @Test
    void deleteMember() {
        // given
        Member member = mock(Member.class);
        MemberDeleteCommand memberDeleteCommand = new MemberDeleteCommand(PASSWORD1, TOKEN_VALUE1);
        when(authenticationPort.parseMemberId(any())).thenReturn(ID_EXAMPLE);
        when(findMemberPort.findMemberById(any())).thenReturn(member);

        // when, then
        deleteMemberService.deleteMember(memberDeleteCommand);
    }
}