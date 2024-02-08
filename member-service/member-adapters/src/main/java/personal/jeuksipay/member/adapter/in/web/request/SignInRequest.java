package personal.jeuksipay.member.adapter.in.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignInRequest {
    private static final String EMAIL_OR_USERNAME_VALUE = "이메일 주소 또는 사용자 이름(아이디)";

    @ApiModelProperty(value = EMAIL_OR_USERNAME_VALUE, example = EMAIL_EXAMPLE)
    private String emailOrUsername;

    @ApiModelProperty(value = PASSWORD_VALUE, example = PASSWORD_EXAMPLE)
    private String password;

    public SignInRequest(String emailOrUsername, String password) {
        this.emailOrUsername = null;
        this.password = null;
    }
}
