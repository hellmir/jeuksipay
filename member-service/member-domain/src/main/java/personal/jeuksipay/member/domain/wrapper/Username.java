package personal.jeuksipay.member.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import personal.jeuksipay.member.domain.exception.general.DuplicateUsernameException;
import personal.jeuksipay.member.domain.security.CryptoProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.USERNAME_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.DuplicateExceptionMessage.DUPLICATE_USERNAME_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_USERNAME_EXCEPTION;

public class Username {
    private final String username;
    private static final String USERNAME_PATTERN = "^[a-z0-9]{4,16}$";

    private Username(String username) {
        this.username = username;
    }

    @JsonCreator
    public static Username of(@JsonProperty("username") String username) {
        validate(username);

        return new Username(username);
    }

    public Username encrypt(CryptoProvider cryptoProvider) {
        return new Username(cryptoProvider.encrypt(username));
    }

    public Username decrypt(CryptoProvider cryptoProvider) {
        return new Username(cryptoProvider.decrypt(username));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username1 = (Username) o;
        return Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public void throwDuplicateException(CryptoProvider cryptoProvider) {
        throw new DuplicateUsernameException(DUPLICATE_USERNAME_EXCEPTION + cryptoProvider.decrypt(username));

    }

    private static void validate(String username) {
        checkUsernameIsNotBlank(username);
        checkUsernamePattern(username);
    }

    private static void checkUsernameIsNotBlank(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(USERNAME_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkUsernamePattern(String username) {
        if (!username.matches(USERNAME_PATTERN)) {
            throw new IllegalArgumentException(INVALID_USERNAME_EXCEPTION);
        }
    }

    @Converter
    public static class UsernameConverter implements AttributeConverter<Username, String> {
        @Override
        public String convertToDatabaseColumn(Username username) {
            return username == null ? null : username.username;
        }

        @Override
        public Username convertToEntityAttribute(String username) {
            return username == null ? null : new Username(username);
        }
    }
}
