package personal.jeuksipay.member.application.port.in.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
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
}
