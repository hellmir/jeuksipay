package personal.jeuksipay.member.testutil;


import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;

import java.time.LocalDateTime;
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

    public static Member createMember(String email, String username, String password, PasswordEncoder passwordEncoder,
                                      String fullName, String phone, List<String> roles) {
        return Member.builder()
                .email(Email.of(email))
                .username(Username.of(username))
                .password(Password.from(password, passwordEncoder))
                .fullName(FullName.of(fullName))
                .phone(Phone.of(phone))
                .address(Address.builder()
                        .city("서울")
                        .street("테헤란로 2길 5")
                        .zipcode("12345")
                        .detailedAddress("101동 102호")
                        .build())
                .roles(Roles.from(roles))
                .lastLoggedInAt(LocalDateTime.now())
                .build();
    }

    public static Member createMember(String id, String email, String username, String password,
                                      PasswordEncoder passwordEncoder, String fullName, String phone, List<String> roles) {
        return Member.builder()
                .id(Long.valueOf(id))
                .email(Email.of(email))
                .username(Username.of(username))
                .password(Password.from(password, passwordEncoder))
                .fullName(FullName.of(fullName))
                .phone(Phone.of(phone))
                .address(Address.builder()
                        .city("서울")
                        .street("테헤란로 2길 5")
                        .zipcode("12345")
                        .detailedAddress("101동 102호")
                        .build())
                .roles(Roles.from(roles))
                .lastLoggedInAt(LocalDateTime.now())
                .build();
    }
}
