package personal.jeuksipay.member.domain.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.AttributeConverter;
import java.util.Objects;

import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.PASSWORD_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_PASSWORD_EXCEPTION;

public class Password {
    private final String password;
    private static final String PASSWORD_PATTERN
            = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    Password(String password) {
        this.password = password;
    }

    public static Password from(String password, PasswordEncoder passwordEncoder) {
        validate(password);

        return new Password(passwordEncoder.encode(password));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    public boolean matchOriginalPassword(PasswordEncoder passwordEncoder, String unencryptedPassword) {
        return passwordEncoder.matches(unencryptedPassword, password);
    }

    private static void validate(String password) {
        checkPasswordIsNotBlank(password);
        checkPasswordPattern(password);
    }

    private static void checkPasswordIsNotBlank(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(PASSWORD_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkPasswordPattern(String password) {
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException(INVALID_PASSWORD_EXCEPTION);
        }
    }

    public static class PasswordConverter implements AttributeConverter<Password, String> {
        @Override
        public String convertToDatabaseColumn(Password password) {
            return password == null ? null : password.password;
        }

        @Override
        public Password convertToEntityAttribute(String password) {
            return password == null ? null : new Password(password);
        }
    }
}
