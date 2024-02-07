package personal.jeuksipay.member.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.MemberTestConstant.USERNAME1;
import static personal.jeuksipay.member.MemberTestConstant.USERNAME2;
import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.USERNAME_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_USERNAME_EXCEPTION;

class UsernameTest {
    @DisplayName("동일한 사용자 이름을 전송하면 동등한 Username 인스턴스를 생성한다.")
    @Test
    void ofSameValue() {
        // given, when
        Username username1 = Username.of(USERNAME1);
        Username username2 = Username.of(USERNAME1);

        // then
        assertThat(username1).isEqualTo(username2);
        assertThat(username1.hashCode()).isEqualTo(username2.hashCode());
    }

    @DisplayName("다른 사용자 이름을 전송하면 동등하지 않은 Username 인스턴스를 생성한다.")
    @Test
    void ofDifferentValue() {
        // given, when
        Username username1 = Username.of(USERNAME1);
        Username username2 = Username.of(USERNAME2);

        // then
        assertThat(username1).isNotEqualTo(username2);
        assertThat(username1.hashCode()).isNotEqualTo(username2.hashCode());
    }

    @DisplayName("사용자 이름을 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void ofBlankValue(String username) {
        // given, when, then
        assertThatThrownBy(() -> Username.of(username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(USERNAME_NO_VALUE_EXCEPTION);
    }

    @DisplayName("유효하지 않은 사용자 이름을 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"ab c", "abc!", "abCde"})
    void ofInvalidValue(String username) {
        // given, when, then
        assertThatThrownBy(() -> Username.of(username))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_USERNAME_EXCEPTION);
    }
}