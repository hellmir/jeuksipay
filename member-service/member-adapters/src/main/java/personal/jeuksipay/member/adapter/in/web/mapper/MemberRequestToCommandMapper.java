package personal.jeuksipay.member.adapter.in.web.mapper;

import personal.jeuksipay.member.adapter.in.web.container.AddressRequest;
import personal.jeuksipay.member.adapter.in.web.request.EmailUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.request.PhoneUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.request.SignInRequest;
import personal.jeuksipay.member.adapter.in.web.request.SignUpRequest;
import personal.jeuksipay.member.application.port.in.command.*;

public class MemberRequestToCommandMapper {
    public static SignUpCommand mapToCommand(SignUpRequest signUpRequest) {
        return SignUpCommand.builder()
                .email(signUpRequest.getEmail())
                .username(signUpRequest.getUsername())
                .password(signUpRequest.getPassword())
                .passwordConfirm(signUpRequest.getPasswordConfirm())
                .fullName(signUpRequest.getFullName())
                .phone(signUpRequest.getPhone())
                .addressCommand(mapToAddressCommand(signUpRequest.getAddressRequest()))
                .roles(signUpRequest.getRoles())
                .build();
    }

    public static signInCommand mapToCommand(SignInRequest signInRequest) {
        return new signInCommand(signInRequest.getEmailOrUsername(), signInRequest.getPassword());
    }

    public static EmailUpdateCommand mapToCommand(EmailUpdateRequest emailUpdateRequest) {
        return new EmailUpdateCommand(
                emailUpdateRequest.getPassword(), emailUpdateRequest.getAccessToken(), emailUpdateRequest.getEmail()
        );
    }

    public static PhoneUpdateCommand mapToCommand(PhoneUpdateRequest phoneUpdateRequest) {
        return new PhoneUpdateCommand(
                phoneUpdateRequest.getPassword(), phoneUpdateRequest.getAccessToken(), phoneUpdateRequest.getPhone()
        );
    }

    public static AddressCommand mapToAddressCommand(AddressRequest addressRequest) {
        return AddressCommand.builder()
                .city(addressRequest.getCity())
                .street(addressRequest.getStreet())
                .zipcode(addressRequest.getZipcode())
                .detailedAddress(addressRequest.getDetailedAddress())
                .accessToken(addressRequest.getAccessToken())
                .build();
    }
}
