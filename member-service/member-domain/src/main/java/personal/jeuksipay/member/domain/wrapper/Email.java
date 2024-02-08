package personal.jeuksipay.member.domain.wrapper;

import personal.jeuksipay.member.domain.exception.general.DuplicateMemberException;
import personal.jeuksipay.member.domain.security.CryptoProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.EMAIL_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.DuplicateExceptionMessage.DUPLICATE_EMAIL_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_EMAIL_EXCEPTION;

public class Email {
    private final String email;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    private Email(String email) {
        this.email = email;
    }

    public static Email of(String email) {
        validate(email);

        return new Email(email);
    }

    public Email encrypt(CryptoProvider cryptoProvider) {
        return new Email(cryptoProvider.encrypt(email));
    }

    public Email decrypt(CryptoProvider cryptoProvider) {
        return new Email(cryptoProvider.decrypt(email));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public void throwDuplicateException(CryptoProvider cryptoProvider) {
        throw new DuplicateMemberException(DUPLICATE_EMAIL_EXCEPTION + cryptoProvider.decrypt(email));
    }

    private static void validate(String email) {
        checkEmailIsNotBlank(email);
        checkEmailPattern(email);
    }

    private static void checkEmailIsNotBlank(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(EMAIL_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkEmailPattern(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException(INVALID_EMAIL_EXCEPTION);
        }
    }

    @Converter
    public static class EmailConverter implements AttributeConverter<Email, String> {
        @Override
        public String convertToDatabaseColumn(Email email) {
            return email == null ? null : email.email;
        }

        @Override
        public Email convertToEntityAttribute(String email) {
            return email == null ? null : new Email(email);
        }
    }
}
