package personal.jeuksipay.member.adapter.in.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PASSWORD_EXAMPLE;
import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PASSWORD_VALUE;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PhoneUpdateRequest {
    private static final String ACCESS_TOKEN_VALUE = "엑세스 토큰 값";
    private static final String ACCESS_TOKEN_EXAMPLE = "accessToken";
    private static final String PHONE_TO_CHANGE_VALUE = "변경할 전화번호";
    private static final String PHONE_TO_CHANGE_EXAMPLE = "01012345679";

    @ApiModelProperty(value = PASSWORD_VALUE, example = PASSWORD_EXAMPLE)
    private String password;

    @ApiModelProperty(value = ACCESS_TOKEN_VALUE, example = ACCESS_TOKEN_EXAMPLE)
    private String accessToken;

    @ApiModelProperty(value = PHONE_TO_CHANGE_VALUE, example = PHONE_TO_CHANGE_EXAMPLE)
    private String phone;

    public PhoneUpdateRequest(String password, String accessToken, String phone) {
        this.password = password;
        this.accessToken = accessToken;
        this.phone = phone;
    }
}
