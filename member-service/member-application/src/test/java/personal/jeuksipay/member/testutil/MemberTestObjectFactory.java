package personal.jeuksipay.member.testutil;


import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;

import java.util.List;

import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

public class MemberTestObjectFactory {
    public static SignUpCommand createSignUpCommand(String email, String username, String password, String password1,
                                                    String fullName, String phone, List<String> role) {
        return SignUpCommand.builder()
                .email(email)
                .username(username)
                .password(password)
                .passwordConfirm(password1)
                .fullName(fullName)
                .phone(phone)
                .addressCommand(AddressCommand.builder()
                        .city(CITY)
                        .street(STREET)
                        .zipcode(ZIPCODE)
                        .detailedAddress(DETAILED_ADDRESS)
                        .build())
                .roles(role)
                .build();
    }
}
