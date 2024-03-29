package personal.jeuksipay.member.adapter.out.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.member.adapter.out.persistence.member.MemberJpaEntity;
import personal.jeuksipay.member.adapter.out.persistence.member.MemberRepository;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.RefreshToken;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CryptoProvider cryptoProvider;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 ID와 권한 정보를 통해 인증을 위한 엑세스 토큰을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "ROLE_GENERAL_USER, ROLE_BUSINESS_USER", "ROLE_BUSINESS_USER, ROLE_ADMIN", "ROLE_ADMIN, ROLE_GENERAL_USER"
    })
    void generateAccessToken(String role1, String role2) {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(role1, role2)
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        memberRepository.save(memberJpaEntity);

        member.setId(memberJpaEntity.getId());

        // when
        String accessToken = jwtTokenProvider.generateAccessToken(member);

        // then
        assertThat(jwtTokenProvider.validateToken(accessToken)).isTrue();
        assertThat(jwtTokenProvider.parseMemberId(accessToken)).isEqualTo(member.getId().toString());
        assertThat(jwtTokenProvider.getAuthentication(accessToken).getAuthorities()).hasSize(2)
                .extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder(role1, role2);
    }

    @DisplayName("회원 ID와 권한 정보를 통해 엑세스 토큰 재발급을 위한 리프레시 토큰을 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "ROLE_GENERAL_USER, ROLE_BUSINESS_USER", "ROLE_BUSINESS_USER, ROLE_ADMIN", "ROLE_ADMIN, ROLE_GENERAL_USER"
    })
    void generateRefreshToken(String role1, String role2) {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(role1, role2)
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        memberRepository.save(memberJpaEntity);

        member.setId(memberJpaEntity.getId());

        // when
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);

        // then
        assertThat(jwtTokenProvider.validateToken(refreshToken)).isTrue();
        assertThat(jwtTokenProvider.parseMemberId(refreshToken)).isEqualTo(member.getId().toString());
        assertThat(jwtTokenProvider.getAuthentication(refreshToken).getAuthorities()).hasSize(2)
                .extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder(role1, role2);
    }

    @DisplayName("리프레시 토큰의 회원 ID와 권한 정보를 통해 엑세스 엑세스 토큰을 재발급할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "ROLE_GENERAL_USER, ROLE_BUSINESS_USER", "ROLE_BUSINESS_USER, ROLE_ADMIN", "ROLE_ADMIN, ROLE_GENERAL_USER"
    })
    void generateNewAccessToken(String role1, String role2) {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(role1, role2)
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        memberRepository.save(memberJpaEntity);

        member.setId(memberJpaEntity.getId());
        RefreshToken refreshToken = RefreshToken.of(member.getId().toString(), List.of(role1, role2), TOKEN_VALUE1);

        // when
        String newAccessToken = jwtTokenProvider.generateNewAccessToken(refreshToken);

        // then
        assertThat(jwtTokenProvider.validateToken(newAccessToken)).isTrue();
        assertThat(jwtTokenProvider.parseMemberId(newAccessToken)).isEqualTo(member.getId().toString());
        assertThat(jwtTokenProvider.getAuthentication(newAccessToken).getAuthorities()).hasSize(2)
                .extracting(GrantedAuthority::getAuthority)
                .containsExactlyInAnyOrder(role1, role2);
    }
}
