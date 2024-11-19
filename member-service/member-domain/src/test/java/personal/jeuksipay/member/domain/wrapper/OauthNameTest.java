package personal.jeuksipay.member.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static personal.jeuksipay.member.MemberTestConstant.OAUTH_NAME1;
import static personal.jeuksipay.member.MemberTestConstant.OAUTH_NAME2;

class OauthNameTest {
    @DisplayName("동일한 OauthName을 전송하면 동등한 OauthName 인스턴스를 생성한다.")
    @Test
    void ofSameValue() {
        // given, when
        OauthName oauthName1 = OauthName.of(OAUTH_NAME1);
        OauthName oauthName2 = OauthName.of(OAUTH_NAME1);

        // then
        assertThat(oauthName1).isEqualTo(oauthName2);
        assertThat(oauthName1.hashCode()).isEqualTo(oauthName2.hashCode());
    }

    @DisplayName("다른 OauthName을 전송하면 동등하지 않은 OauthName 인스턴스를 생성한다.")
    @Test
    void ofDifferentValue() {
        // given, when
        OauthName oauthName1 = OauthName.of(OAUTH_NAME1);
        OauthName oauthName2 = OauthName.of(OAUTH_NAME2);


        // then
        assertThat(oauthName1).isNotEqualTo(oauthName2);
        assertThat(oauthName1.hashCode()).isNotEqualTo(oauthName2.hashCode());
    }
}
