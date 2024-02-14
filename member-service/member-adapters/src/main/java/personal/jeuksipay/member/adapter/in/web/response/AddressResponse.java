package personal.jeuksipay.member.adapter.in.web.response;

import lombok.Builder;
import lombok.Getter;
import personal.jeuksipay.member.domain.Address;

@Builder
@Getter
public class AddressResponse {
    private String city;
    private String street;
    private String zipcode;
    private String detailedAddress;

    public static AddressResponse from(Address address) {
        return AddressResponse.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .detailedAddress(address.getDetailedAddress())
                .build();
    }
}
