package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.RefreshToken;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.adapter.out.persistence.refreshtoken.RefreshTokenPersistenceAdapter.INVALID_TOKEN_EXCEPTION_MESSAGE;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_ADMIN;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class RefreshTokenPersistenceAdapterTest {
    @Autowired
    private RefreshTokenPersistenceAdapter refreshTokenPersistenceAdapter;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthenticationPort authenticationPort;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("리프레시 토큰 정보를 데이터베이스에 저장할 수 있다.")
    @Test
    void saveRefreshToken() {
        // given
        String role1 = ROLE_GENERAL_USER.toString();
        String role2 = ROLE_ADMIN.toString();

        RefreshToken refreshToken = RefreshToken.of(ID_EXAMPLE, List.of(role1, role2), TOKEN_VALUE1);

        // when
        refreshTokenPersistenceAdapter.saveRefreshToken(refreshToken);
        RefreshTokenJpaEntity refreshTokenJpaEntity
                = refreshTokenRepository.findByTokenValue(refreshToken.getTokenValue()).get();

        // then
        assertThat(refreshTokenJpaEntity.getId()).isNotNull();
        assertThat(refreshTokenJpaEntity.getMemberId()).isEqualTo(refreshToken.getMemberId());
        assertThat(refreshTokenJpaEntity.getRoles()).hasSize(2).containsExactlyInAnyOrder(role1, role2);
        assertThat(refreshTokenJpaEntity.getTokenValue()).isEqualTo(refreshToken.getTokenValue());
    }

    @DisplayName("리프레시 토큰 값을 통해 리프레시 토큰 도메인 엔티티를 조회할 수 있다.")
    @Test
    void findRefreshToken() {
        // given
        RefreshToken createdRefreshToken = RefreshToken.of(ID_EXAMPLE, List.of(ROLE_GENERAL_USER.toString()), TOKEN_VALUE1);
        RefreshTokenJpaEntity refreshTokenJpaEntity = RefreshTokenJpaEntity.from(createdRefreshToken);
        refreshTokenRepository.save(refreshTokenJpaEntity);

        // when
        RefreshToken foundRefreshToken
                = refreshTokenPersistenceAdapter.findRefreshToken(createdRefreshToken.getTokenValue());

        // then
        assertThat(foundRefreshToken).isEqualTo(createdRefreshToken);
        assertThat(foundRefreshToken.getTokenValue()).isNotNull();
    }

    @DisplayName("존재하지 않는 리프레시 토큰 값을 통해 엑세스 토큰을 발급하려 하면 IllegalArgumentException이 발생한다.")
    @Test
    void findRefreshTokenFromNonExistentRefreshTokenValue() {
        // given
        Member member = MemberTestObjectFactory.createMember(
                ID_EXAMPLE, EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        String refreshTokenValue = authenticationPort.generateRefreshToken(member);

        // when, then
        assertThatThrownBy(() -> refreshTokenPersistenceAdapter.findRefreshToken(refreshTokenValue))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_TOKEN_EXCEPTION_MESSAGE + refreshTokenValue);
    }
}
