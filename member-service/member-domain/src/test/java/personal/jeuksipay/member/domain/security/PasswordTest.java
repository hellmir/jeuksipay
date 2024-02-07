package personal.jeuksipay.member.domain.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.MemberTestConstant.PASSWORD1;
import static personal.jeuksipay.member.MemberTestConstant.PASSWORD2;
import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.PASSWORD_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_PASSWORD_EXCEPTION;


class PasswordTest {
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @DisplayName("동일한 비밀번호를 전송하면 동등한 Password 인스턴스를 생성한다.")
    @Test
    void fromSameValue() {
        // given, when
        Password password1 = new Password(PASSWORD1);
        Password password2 = new Password(PASSWORD1);

        // then
        assertThat(password1).isEqualTo(password2);
    }

    @DisplayName("다른 비밀번호를 전송하면 동등하지 않은 Password 인스턴스를 생성한다.")
    @Test
    void fromDifferentValue() {
        // given, when
        Password password1 = new Password(PASSWORD1);
        Password password2 = new Password(PASSWORD2);

        // then
        assertThat(password1).isNotEqualTo(password2);
        assertThat(password1.hashCode()).isNotEqualTo(password2.hashCode());
    }

    @DisplayName("같은 비밀번호를 전송해 인코딩하면 동등하지 않은 Password 인스턴스를 생성한다.")
    @Test
    void fromSameValueWithEncoding() {
        // given, when
        Password password1 = Password.from(PASSWORD1, passwordEncoder);
        Password password2 = Password.from(PASSWORD1, passwordEncoder);

        // then
        assertThat(password1).isNotEqualTo(password2);
        assertThat(password1.hashCode()).isNotEqualTo(password2.hashCode());
    }

    @DisplayName("비밀번호를 전송해 인코딩한 후 원래 비밀번호의 일치 여부를 확인할 수 있다.")
    @Test
    void fromValueWithEncoding() {
        // given, when
        Password password = Password.from(PASSWORD1, passwordEncoder);

        // then
        assertThat(password.matchOriginalPassword(passwordEncoder, PASSWORD1)).isTrue();
        assertThat(password.matchOriginalPassword(passwordEncoder, PASSWORD2)).isFalse();
    }

    @DisplayName("비밀번호를 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @Test
    void ofBlankValue() {
        // given
        String password1 = null;
        String password2 = "";
        String password3 = " ";

        // when, then
        assertThatThrownBy(() -> Password.from(password1, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PASSWORD_NO_VALUE_EXCEPTION);

        assertThatThrownBy(() -> Password.from(password2, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PASSWORD_NO_VALUE_EXCEPTION);

        assertThatThrownBy(() -> Password.from(password3, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PASSWORD_NO_VALUE_EXCEPTION);
    }

    @DisplayName("유효하지 않은 비밀번호를 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"abcD1!", "1234!abcde", "AbCdE12345", "!@1234ABCDE"})
    void ofInvalidValue(String password) {
        // given, when, then
        assertThatThrownBy(() -> Password.from(password, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PASSWORD_EXCEPTION);
    }
}