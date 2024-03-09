package personal.jeuksipay.member.application.port.in.command;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class AddressCommand {
    private String city;
    private String street;
    private String zipcode;
    private String detailedAddress;
    private String accessToken;
}
