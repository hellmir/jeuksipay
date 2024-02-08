package personal.jeuksipay.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_ADMIN;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ExtendWith(MockitoExtension.class)
class GetMemberServiceTest {
    @InjectMocks
    private GetMemberService getMemberService;

    @Mock
    private FindMemberPort findMemberPort;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @DisplayName("CustomUserDetails를 통해 회원 정보를 불러 올 수 있다.")
    @Test
    void loadUserByUsername() {
        // given
        String role1 = ROLE_GENERAL_USER.toString();
        String role2 = ROLE_ADMIN.toString();

        Member member = MemberTestObjectFactory.createMember(
                ID_EXAMPLE, EMAIL, USERNAME, PASSWORD1, passwordEncoder, FULL_NAME, PHONE, List.of(role1, role2)
        );
        when(findMemberPort.findMemberById(anyLong())).thenReturn(member);

        // when
        UserDetails userDetails = getMemberService.loadUserByUsername(ID_EXAMPLE);

        // then
        assertThat(passwordEncoder.matches(PASSWORD1, userDetails.getPassword())).isTrue();
        assertThat(userDetails.getUsername()).isEqualTo(ID_EXAMPLE);
        assertThat(userDetails.isAccountNonExpired()).isTrue();
        assertThat(userDetails.isAccountNonLocked()).isTrue();
        assertThat(userDetails.isCredentialsNonExpired()).isTrue();
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities()).hasSize(2)
                .extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder(role1, role2);
    }
}