package personal.jeuksipay.member.domain.wrapper;

import personal.jeuksipay.member.domain.security.CryptoProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.PHONE_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_PHONE_EXCEPTION;

public class Phone {
    private final String phone;
    private static final String PHONE_PATTERN = "^010\\d{8}$";

    private Phone(String phone) {
        this.phone = phone;
    }

    public static Phone of(String phone) {
        validate(phone);

        return new Phone(phone);
    }

    public Phone encrypt(CryptoProvider cryptoProvider) {
        return new Phone(cryptoProvider.encrypt(phone));
    }

    public Phone decrypt(CryptoProvider cryptoProvider) {
        return new Phone(cryptoProvider.decrypt(phone));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone1 = (Phone) o;
        return Objects.equals(phone, phone1.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

    String getValue() {
        return phone;
    }

    private static void validate(String phone) {
        checkPhoneIsNotBlank(phone);
        checkPhonePattern(phone);
    }

    private static void checkPhoneIsNotBlank(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException(PHONE_NO_VALUE_EXCEPTION);
        }
    }

    private static void checkPhonePattern(String phone) {
        if (!phone.matches(PHONE_PATTERN)) {
            throw new IllegalArgumentException(INVALID_PHONE_EXCEPTION);
        }
    }

    @Converter
    public static class PhoneConverter implements AttributeConverter<Phone, String> {
        @Override
        public String convertToDatabaseColumn(Phone phone) {
            return phone == null ? null : phone.phone;
        }

        @Override
        public Phone convertToEntityAttribute(String phone) {
            return phone == null ? null : new Phone(phone);
        }
    }
}
