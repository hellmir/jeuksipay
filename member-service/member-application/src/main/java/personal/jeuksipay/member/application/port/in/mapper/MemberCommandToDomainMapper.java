package personal.jeuksipay.member.application.port.in.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;

import java.time.LocalDateTime;

public class MemberCommandToDomainMapper {
    public static Member mapToDomainEntity(SignUpCommand signUpCommand, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(Email.of(signUpCommand.getEmail()))
                .username(Username.of(signUpCommand.getUsername()))
                .password(Password.from(signUpCommand.getPassword(), passwordEncoder))
                .fullName(FullName.of(signUpCommand.getFullName()))
                .phone(Phone.of(signUpCommand.getPhone()))
                .address(mapToAddress(signUpCommand.getAddressCommand()))
                .roles(Roles.from(signUpCommand.getRoles()))
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public static Address mapToAddress(AddressCommand addressCommand) {
        String city = addressCommand.getCity();
        String street = addressCommand.getStreet();
        String zipcode = addressCommand.getZipcode();
        String detailedAddress = addressCommand.getDetailedAddress();

        return Address.of(city, street, zipcode, detailedAddress);
    }
}
