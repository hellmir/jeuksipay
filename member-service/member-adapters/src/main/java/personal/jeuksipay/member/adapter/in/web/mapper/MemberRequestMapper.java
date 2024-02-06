package personal.jeuksipay.member.adapter.in.web.mapper;

import personal.jeuksipay.member.adapter.in.web.container.AddressRequest;
import personal.jeuksipay.member.adapter.in.web.request.SignUpRequest;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;

public class MemberRequestMapper {
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

    private static AddressCommand mapToAddressCommand(AddressRequest addressRequest) {
        return AddressCommand.builder()
                .city(addressRequest.getCity())
                .street(addressRequest.getStreet())
                .zipcode(addressRequest.getZipcode())
                .detailedAddress(addressRequest.getDetailedAddress())
                .build();
    }
}
