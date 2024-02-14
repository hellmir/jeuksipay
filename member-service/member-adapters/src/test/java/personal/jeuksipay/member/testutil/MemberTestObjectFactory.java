package personal.jeuksipay.member.testutil;


import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.adapter.in.web.container.AddressRequest;
import personal.jeuksipay.member.adapter.in.web.request.SignUpRequest;
import personal.jeuksipay.member.adapter.out.persistence.member.MemberJpaEntity;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;

import java.util.List;

import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

public class MemberTestObjectFactory {
    public static SignUpRequest enterMemberForm(String email, String username, String password, String passwordConfirm,
                                                String fullName, String phone, List<String> roles) {
        return SignUpRequest.builder()
                .email(email)
                .username(username)
                .password(password)
                .passwordConfirm(passwordConfirm)
                .fullName(fullName)
                .phone(phone)
                .addressRequest(AddressRequest.builder()
                        .city(CITY)
                        .street(STREET)
                        .zipcode(ZIPCODE)
                        .detailedAddress(DETAILED_ADDRESS)
                        .build())
                .roles(roles)
                .build();
    }

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
                        .city(CITY)
                        .street(STREET)
                        .zipcode(ZIPCODE)
                        .detailedAddress(DETAILED_ADDRESS)
                        .build())
                .roles(Roles.from(roles))
                .build();
    }

    public static Member createMember(String id, String email, String username,
                                      String password, PasswordEncoder passwordEncoder,
                                      String fullName, String phone, List<String> roles) {
        return Member.builder()
                .id(Long.parseLong(id))
                .email(Email.of(email))
                .username(Username.of(username))
                .password(Password.from(password, passwordEncoder))
                .fullName(FullName.of(fullName))
                .phone(Phone.of(phone))
                .address(Address.builder()
                        .city(CITY)
                        .street(STREET)
                        .zipcode(ZIPCODE)
                        .detailedAddress(DETAILED_ADDRESS)
                        .build())
                .roles(Roles.from(roles))
                .build();
    }

    public static MemberJpaEntity createMemberJpaEntity(String email, String username, String password,
                                                        PasswordEncoder passwordEncoder, String fullName,
                                                        String phone, List<String> roles,
                                                        CryptoProvider cryptoProvider) {
        return MemberJpaEntity.builder()
                .email(Email.of(email).encrypt(cryptoProvider))
                .username(Username.of(username).encrypt(cryptoProvider))
                .password(Password.from(password, passwordEncoder))
                .fullName(FullName.of(fullName).encrypt(cryptoProvider))
                .phone(Phone.of(phone).encrypt(cryptoProvider))
                .address(Address.builder()
                        .city(CITY)
                        .street(STREET)
                        .zipcode(ZIPCODE)
                        .detailedAddress(DETAILED_ADDRESS)
                        .build()
                        .encrypt(cryptoProvider))
                .roles(Roles.from(roles))
                .build();
    }
}
