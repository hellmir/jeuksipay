package personal.jeuksipay.member.domain.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import personal.jeuksipay.member.domain.security.CryptoProvider;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

public class OauthName {
    private final String oauthName;

    private OauthName(String oauthName) {
        this.oauthName = oauthName;
    }

    @JsonCreator
    public static OauthName of(@JsonProperty("oauthName") String oauthName) {
        return new OauthName(oauthName);
    }

    public OauthName encrypt(CryptoProvider cryptoProvider) {
        return new OauthName(cryptoProvider.encrypt(oauthName));
    }

    public OauthName decrypt(CryptoProvider cryptoProvider) {
        return new OauthName(cryptoProvider.decrypt(oauthName));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OauthName oauthName1 = (OauthName) object;
        return Objects.equals(oauthName, oauthName1.oauthName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oauthName);
    }

    @Converter
    public static class OauthNameConverter implements AttributeConverter<OauthName, String> {
        @Override
        public String convertToDatabaseColumn(OauthName oauthName) {
            return oauthName == null ? null : oauthName.oauthName;
        }

        @Override
        public OauthName convertToEntityAttribute(String oauthName) {
            return oauthName == null ? null : new OauthName(oauthName);
        }
    }
}
