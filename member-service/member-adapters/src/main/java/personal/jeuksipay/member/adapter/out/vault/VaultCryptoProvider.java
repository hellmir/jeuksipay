package personal.jeuksipay.member.adapter.out.vault;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import personal.jeuksipay.member.domain.security.CryptoProvider;

@Component
@RequiredArgsConstructor
public class VaultCryptoProvider implements CryptoProvider {
    private final VaultAdapter vaultAdapter;

    @Override
    public String encrypt(String value) {
        return vaultAdapter.encrypt(value);
    }

    @Override
    public String decrypt(String value) {
        return vaultAdapter.decrypt(value);
    }
}
