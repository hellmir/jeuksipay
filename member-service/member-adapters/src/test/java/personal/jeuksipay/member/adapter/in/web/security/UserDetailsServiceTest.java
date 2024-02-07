package personal.jeuksipay.member.adapter.in.web.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import personal.jeuksipay.member.adapter.out.mapper.MemberJpaEntityToDomainMapper;
import personal.jeuksipay.member.adapter.out.persistence.MemberJpaEntity;
import personal.jeuksipay.member.adapter.out.persistence.MemberRepository;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.application.port.in.usecase.SignUpUseCase;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.security.CustomUserDetails;
import personal.jeuksipay.member.testutil.MemberTestConstant;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static personal.jeuksipay.member.domain.wrapper.Role.*;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@SpringBootTest
class UserDetailsServiceTest {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SignUpUseCase signUpUseCase;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CryptoProvider cryptoProvider;

    @DisplayName("가입된 사용자의 인증 정보와 권한 정보를 불러 올 수 있다.")
    @Test
    void loadUserByUsername() {
        // given
        List<String> roles1 = List.of(ROLE_BUSINESS_USER.toString());
        List<String> roles2 = List.of(ROLE_GENERAL_USER.toString());
        List<String> roles3 = List.of(ROLE_ADMIN.toString());
        List<String> roles4 = List.of(ROLE_GENERAL_USER.toString(), ROLE_ADMIN.toString());

        SignUpCommand signUpCommand1 = MemberTestObjectFactory.createSignUpCommand
                (EMAIL1, USERNAME1, MemberTestConstant.PASSWORD1, PASSWORD1, FULL_NAME1, PHONE1, roles1);

        SignUpCommand signUpCommand2 = MemberTestObjectFactory.createSignUpCommand
                (EMAIL2, USERNAME2, PASSWORD2, PASSWORD2, FULL_NAME2, PHONE2, roles2);

        SignUpCommand signUpCommand3 = MemberTestObjectFactory.createSignUpCommand
                (EMAIL3, USERNAME3, PASSWORD3, PASSWORD3, FULL_NAME3, PHONE3, roles3);

        SignUpCommand signUpCommand4 = MemberTestObjectFactory.createSignUpCommand
                (EMAIL4, USERNAME4, PASSWORD4, PASSWORD4, FULL_NAME4, PHONE4, roles4);

        signUpUseCase.createMember(signUpCommand1);
        signUpUseCase.createMember(signUpCommand2);
        signUpUseCase.createMember(signUpCommand3);
        signUpUseCase.createMember(signUpCommand4);

        List<MemberJpaEntity> memberJpaEntities = memberRepository.findAll();

        Member member1 = MemberJpaEntityToDomainMapper.mapToDomainEntity(memberJpaEntities.get(0), cryptoProvider);
        Member member2 = MemberJpaEntityToDomainMapper.mapToDomainEntity(memberJpaEntities.get(1), cryptoProvider);
        Member member3 = MemberJpaEntityToDomainMapper.mapToDomainEntity(memberJpaEntities.get(2), cryptoProvider);
        Member member4 = MemberJpaEntityToDomainMapper.mapToDomainEntity(memberJpaEntities.get(3), cryptoProvider);

        String memberId1 = String.valueOf(member1.getId());
        String memberId2 = String.valueOf(member2.getId());
        String memberId3 = String.valueOf(member3.getId());
        String memberId4 = String.valueOf(member4.getId());

        // when
        UserDetails user1 = userDetailsService.loadUserByUsername(memberId1);
        UserDetails user2 = userDetailsService.loadUserByUsername(memberId2);
        UserDetails user3 = userDetailsService.loadUserByUsername(memberId3);
        UserDetails user4 = userDetailsService.loadUserByUsername(memberId4);

        // then
        assertThat(user1.getUsername()).isEqualTo(memberId1);
        assertThat(user1.getAuthorities()).isEqualTo(new CustomUserDetails(member1).getAuthorities());

        assertThat(user2.getUsername()).isEqualTo(memberId2);
        assertThat(user2.getAuthorities()).isEqualTo(new CustomUserDetails(member2).getAuthorities());

        assertThat(user3.getUsername()).isEqualTo(memberId3);
        assertThat(user3.getAuthorities()).isEqualTo(new CustomUserDetails(member3).getAuthorities());

        assertThat(user4.getUsername()).isEqualTo(memberId4);
        assertThat(user4.getAuthorities()).isEqualTo(new CustomUserDetails(member4).getAuthorities());
    }
}