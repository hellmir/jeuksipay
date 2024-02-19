package personal.jeuksipay.member.application.port.in.command;

import lombok.Getter;

@Getter
public class EmailUpdateCommand {
    private final String password;
    private final String accessToken;
    private final String email;

    public EmailUpdateCommand(String password, String accessToken, String email) {
        this.password = password;
        this.accessToken = accessToken;
        this.email = email;
    }
}
