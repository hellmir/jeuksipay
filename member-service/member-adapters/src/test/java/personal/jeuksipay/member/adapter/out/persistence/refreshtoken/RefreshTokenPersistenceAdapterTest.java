package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import personal.jeuksipay.member.domain.RefreshToken;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_ADMIN;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.TOKEN_VALUE;

@ActiveProfiles("test")
@SpringBootTest
class RefreshTokenPersistenceAdapterTest {
    @Autowired
    private RefreshTokenPersistenceAdapter refreshTokenPersistenceAdapter;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @DisplayName("리프레시 토큰 정보를 데이터베이스에 저장할 수 있다.")
    @Test
    void saveRefreshToken() {
        // given
        String role1 = ROLE_GENERAL_USER.toString();
        String role2 = ROLE_ADMIN.toString();

        RefreshToken refreshToken = RefreshToken.of(ID_EXAMPLE, List.of(role1, role2), TOKEN_VALUE);

        // when
        refreshTokenPersistenceAdapter.saveRefreshToken(refreshToken);
        List<RefreshTokenJpaEntity> refreshTokenJpaEntitys = refreshTokenRepository.findAll();

        // then
        assertThat(refreshTokenJpaEntitys.get(0).getId()).isNotNull();
        assertThat(refreshTokenJpaEntitys.get(0).getMemberId()).isEqualTo(ID_EXAMPLE);
        assertThat(refreshTokenJpaEntitys.get(0).getRoles()).hasSize(2).containsExactlyInAnyOrder(role1, role2);
        assertThat(refreshTokenJpaEntitys.get(0).getTokenValue()).isEqualTo(TOKEN_VALUE);
    }
}