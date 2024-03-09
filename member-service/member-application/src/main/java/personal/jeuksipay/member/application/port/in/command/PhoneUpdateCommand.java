package personal.jeuksipay.member.application.port.in.command;

import lombok.Getter;

@Getter
public class PhoneUpdateCommand {
    private final String password;
    private final String accessToken;
    private final String phone;

    public PhoneUpdateCommand(String password, String accessToken, String phone) {
        this.password = password;
        this.accessToken = accessToken;
        this.phone = phone;
    }
}
