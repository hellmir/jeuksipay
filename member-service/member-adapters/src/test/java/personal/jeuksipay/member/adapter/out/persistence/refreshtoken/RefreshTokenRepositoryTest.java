package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import personal.jeuksipay.member.adapter.out.persistence.member.MemberJpaEntity;
import personal.jeuksipay.member.adapter.out.persistence.member.MemberRepository;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.RefreshToken;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_BUSINESS_USER;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@SpringBootTest
class RefreshTokenRepositoryTest {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationPort authenticationPort;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CryptoProvider cryptoProvider;

    @DisplayName("리프레시 토큰 값을 통해 리프레시 토큰 엔티티를 조회할 수 있다.")
    @Test
    void findByTokenValue() {
        // given
        List<String> roles1 = List.of(ROLE_GENERAL_USER.toString());
        List<String> roles2 = List.of(ROLE_BUSINESS_USER.toString());

        Member member1 = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, roles1
        );
        Member member2 = MemberTestObjectFactory.createMember(
                EMAIL2, USERNAME2, PASSWORD2, passwordEncoder, FULL_NAME2, PHONE2, roles2
        );
        MemberJpaEntity memberJpaEntity1 = MemberJpaEntity.from(member1, cryptoProvider);
        MemberJpaEntity memberJpaEntity2 = MemberJpaEntity.from(member2, cryptoProvider);
        memberRepository.saveAll(List.of(memberJpaEntity1, memberJpaEntity2));

        member1.setId(memberJpaEntity1.getId());
        member2.setId(memberJpaEntity2.getId());

        String refreshTokenValue1 = authenticationPort.generateRefreshToken(member1);
        String refreshTokenValue2 = authenticationPort.generateRefreshToken(member2);

        RefreshToken refreshToken1 = RefreshToken.of(member1.getId().toString(), roles1, refreshTokenValue1);
        RefreshToken refreshToken2 = RefreshToken.of(member2.getId().toString(), roles2, refreshTokenValue2);

        RefreshTokenJpaEntity createdRefreshTokenJpaEntity1 = RefreshTokenJpaEntity.from(refreshToken1);
        RefreshTokenJpaEntity createdRefreshTokenJpaEntity2 = RefreshTokenJpaEntity.from(refreshToken2);
        refreshTokenRepository.saveAll(List.of(createdRefreshTokenJpaEntity1, createdRefreshTokenJpaEntity2));

        // when
        RefreshTokenJpaEntity foundRefreshTokenJpaEntity1
                = refreshTokenRepository.findByTokenValue(refreshTokenValue1).get();
        RefreshTokenJpaEntity foundRefreshTokenJpaEntity2
                = refreshTokenRepository.findByTokenValue(refreshTokenValue2).get();

        // then
        assertThat(foundRefreshTokenJpaEntity1).isEqualTo(createdRefreshTokenJpaEntity1);
        assertThat(foundRefreshTokenJpaEntity2).isEqualTo(createdRefreshTokenJpaEntity2);
        assertThat(foundRefreshTokenJpaEntity1).isNotEqualTo(foundRefreshTokenJpaEntity2);
    }
}