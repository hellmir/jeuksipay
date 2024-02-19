package personal.jeuksipay.member.adapter.in.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PASSWORD_EXAMPLE;
import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PASSWORD_VALUE;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EmailUpdateRequest {
    private static final String ACCESS_TOKEN_VALUE = "엑세스 토큰 값";
    private static final String ACCESS_TOKEN_EXAMPLE = "accessToken";
    private static final String EMAIL_TO_CHANGE_VALUE = "변경할 이메일 주소";
    private static final String EMAIL_TO_CHANGE_EXAMPLE = "abcde@abc.com";

    @ApiModelProperty(value = PASSWORD_VALUE, example = PASSWORD_EXAMPLE)
    private String password;

    @ApiModelProperty(value = ACCESS_TOKEN_VALUE, example = ACCESS_TOKEN_EXAMPLE)
    private String accessToken;

    @ApiModelProperty(value = EMAIL_TO_CHANGE_VALUE, example = EMAIL_TO_CHANGE_EXAMPLE)
    private String email;

    public EmailUpdateRequest(String password, String accessToken, String email) {
        this.password = password;
        this.accessToken = accessToken;
        this.email = email;
    }
}
