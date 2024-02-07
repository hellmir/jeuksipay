package personal.jeuksipay.member.adapter.in.web.container;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class AddressRequest {
    private static final String CITY_VALUE = "지역 이름";
    private static final String CITY_EXAMPLE = "서울";

    private static final String STREET_VALUE = "도로명 주소";
    private static final String STREET_EXAMPLE = "테헤란로 231";

    private static final String ZIPCODE_VALUE = "우편번호";
    private static final String ZIPCODE_EXAMPLE = "06142";

    private static final String DETAILED_ADDRESS_VALUE = "상세 주소";
    private static final String DETAILED_ADDRESS_EXAMPLE = "길동아파트 101동 102호";

    @ApiModelProperty(value = CITY_VALUE, example = CITY_EXAMPLE)
    private String city;

    @ApiModelProperty(value = STREET_VALUE, example = STREET_EXAMPLE)
    private String street;

    @ApiModelProperty(value = ZIPCODE_VALUE, example = ZIPCODE_EXAMPLE)
    private String zipcode;

    @ApiModelProperty(value = DETAILED_ADDRESS_VALUE, example = DETAILED_ADDRESS_EXAMPLE)
    private String detailedAddress;
}
