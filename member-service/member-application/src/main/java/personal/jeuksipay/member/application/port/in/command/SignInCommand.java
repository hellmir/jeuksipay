package personal.jeuksipay.member.application.port.in.command;

import lombok.Getter;

@Getter
public class SignInCommand {
    private final String emailOrUsername;
    private final String password;

    public SignInCommand(String emailOrUsername, String password) {
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }
}
