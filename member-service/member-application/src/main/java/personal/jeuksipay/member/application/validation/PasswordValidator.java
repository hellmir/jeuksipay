package personal.jeuksipay.member.application.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import personal.jeuksipay.member.application.exception.significant.IncorrectPasswordException;
import personal.jeuksipay.member.domain.security.Password;

@Component
@RequiredArgsConstructor
public class PasswordValidator {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void validatePassword(Password encodedPassword, String enteredPassword) {
        if (encodedPassword.matchOriginalPassword(passwordEncoder, enteredPassword)) {
            return;
        }

        throw new IncorrectPasswordException();
    }
}
