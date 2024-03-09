package personal.jeuksipay.member.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.MemberTestConstant.EMAIL1;
import static personal.jeuksipay.member.MemberTestConstant.EMAIL2;
import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.EMAIL_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_EMAIL_EXCEPTION;

class EmailTest {
    @DisplayName("동일한 이메일 주소를 전송하면 동등한 Email 인스턴스를 생성한다.")
    @Test
    void ofSameValue() {
        // given, when
        Email email1 = Email.of(EMAIL1);
        Email email2 = Email.of(EMAIL1);

        // then
        assertThat(email1).isEqualTo(email2);
        assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
    }

    @DisplayName("다른 이메일 주소를 전송하면 동등하지 않은 Email 인스턴스를 생성한다.")
    @Test
    void ofDifferentValue() {
        // given, when
        Email email1 = Email.of(EMAIL1);
        Email email2 = Email.of(EMAIL2);

        // then
        assertThat(email1).isNotEqualTo(email2);
        assertThat(email1.hashCode()).isNotEqualTo(email2.hashCode());
    }

    @DisplayName("이메일 주소를 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void ofBlankValue(String email) {

        // given, when, then
        assertThatThrownBy(() -> Email.of(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMAIL_NO_VALUE_EXCEPTION);
    }

    @DisplayName("유효하지 않은 이메일 주소를 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"abcd@abc,com", "abcabcd.com", "abcde .com"})
    void ofInvalidValue(String email) {
        // given, when, then
        assertThatThrownBy(() -> Email.of(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_EMAIL_EXCEPTION);
    }
}
