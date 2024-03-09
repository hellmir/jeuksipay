package personal.jeuksipay.member.application.port.in.command;

import lombok.Getter;

@Getter
public class PasswordUpdateCommand {
    private String currentPassword;
    private String accessToken;
    private String passwordToChange;
    private String passwordToChangeConfirm;

    public PasswordUpdateCommand(String currentPassword, String accessToken,
                                 String passwordToChange, String passwordToChangeConfirm) {
        this.currentPassword = currentPassword;
        this.accessToken = accessToken;
        this.passwordToChange = passwordToChange;
        this.passwordToChangeConfirm = passwordToChangeConfirm;
    }
}
