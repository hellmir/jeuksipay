package personal.jeuksipay.member.application.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.exception.significant.IncorrectPasswordException;
import personal.jeuksipay.member.domain.security.Password;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.application.exception.message.IncorrectPasswordExceptionMessage.INCORRECT_PASSWORD_EXCEPTION;
import static personal.jeuksipay.member.testutil.MemberTestConstant.PASSWORD1;

class PasswordValidatorTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final PasswordValidator passwordValidator = new PasswordValidator(passwordEncoder);

    @DisplayName("원래 비밀번호와 입력된 비밀번호가 같은지 여부를 검증할 수 있다.")
    @Test
    void validatePassword() {
        // given
        Password encodedPassword = Password.from(PASSWORD1, passwordEncoder);

        // when, then
        passwordValidator.validatePassword(encodedPassword, PASSWORD1);
    }

    @DisplayName("원래 비밀번호와 입력된 비밀번호가 다른 경우 IncorrectPasswordException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"Abcd1234!, Abcd12345!", "Abcd12345!, Abcd1234!", "Abcd123456!, Abcd1234!"
    })
    void signUpMemberWithWrongPasswordConfirm(String originalPassword, String enteredPassword) {
        // given
        Password encodedPassword = Password.from(originalPassword, passwordEncoder);

        // when, then
        assertThatThrownBy(() -> passwordValidator.validatePassword(encodedPassword, enteredPassword))
                .isInstanceOf(IncorrectPasswordException.class)
                .hasMessage(INCORRECT_PASSWORD_EXCEPTION);
    }
}