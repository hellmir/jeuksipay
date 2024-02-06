package personal.jeuksipay.member.application.port.in.command;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
public class SignUpCommand {
    private final String email;
    private final String username;
    private final String password;
    private final String passwordConfirm;
    private final String fullName;
    private final String phone;
    private final AddressCommand addressCommand;
    private final List<String> roles;

    @Builder
    private SignUpCommand(String email, String username, String password, String passwordConfirm, String fullName,
                          String phone, AddressCommand addressCommand, List<String> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.fullName = fullName;
        this.phone = phone;
        this.addressCommand = addressCommand;
        this.roles = roles;
    }
}
