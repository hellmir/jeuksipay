package personal.jeuksipay.member.domain.wrapper;

import personal.jeuksipay.member.domain.security.CryptoProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.FULL_NAME_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_FULL_NAME_EXCEPTION;

public class FullName {
    private final String fullName;
    private static final String FULL_NAME_PATTERN = "^[가-힣]{2,5}$";

    private FullName(String fullName) {
        this.fullName = fullName;
    }

    public static FullName of(String fullName) {
        validate(fullName);

        return new FullName(fullName);
    }

    public FullName encrypt(CryptoProvider cryptoProvider) {
        return new FullName(cryptoProvider.encrypt(fullName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName1 = (FullName) o;
        return Objects.equals(fullName, fullName1.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    private static void validate(String fullName) {
        checkFullNameIsNotBlank(fullName);
        checkFullnamePattern(fullName);
    }

    private static void checkFullNameIsNotBlank(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException(FULL_NAME_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkFullnamePattern(String fullName) {
        if (!fullName.matches(FULL_NAME_PATTERN)) {
            throw new IllegalArgumentException(INVALID_FULL_NAME_EXCEPTION);
        }
    }

    @Converter
    public static class FullNameConverter implements AttributeConverter<FullName, String> {
        @Override
        public String convertToDatabaseColumn(FullName fullName) {
            return fullName == null ? null : fullName.fullName;
        }

        @Override
        public FullName convertToEntityAttribute(String fullName) {
            return fullName == null ? null : new FullName(fullName);
        }
    }
}
