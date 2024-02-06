package personal.jeuksipay.member.domain.security;

public interface CryptoProvider {
    String encrypt(String value);

    String decrypt(String value);
}
