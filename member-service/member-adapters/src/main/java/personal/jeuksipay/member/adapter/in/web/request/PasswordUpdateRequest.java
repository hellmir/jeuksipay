package personal.jeuksipay.member.adapter.in.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PASSWORD_EXAMPLE;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PasswordUpdateRequest {
    private static final String CURRENT_PASSWORD_VALUE = "현재 비밀번호";
    private static final String ACCESS_TOKEN_VALUE = "엑세스 토큰 값";
    private static final String ACCESS_TOKEN_EXAMPLE = "accessToken";

    private static final String PASSWORD_TO_CHANGE_VALUE = "변경할 비밀번호";
    private static final String PASSWORD_TO_CHANGE_CONFIRM_VALUE = "변경할 비밀번호 확인";
    private static final String PASSWORD_TO_CHANGE_EXAMPLE = "Abc1!2@34";

    @ApiModelProperty(value = CURRENT_PASSWORD_VALUE, example = PASSWORD_EXAMPLE)
    private String currentPassword;

    @ApiModelProperty(value = ACCESS_TOKEN_VALUE, example = ACCESS_TOKEN_EXAMPLE)
    private String accessToken;

    @ApiModelProperty(value = PASSWORD_TO_CHANGE_VALUE, example = PASSWORD_TO_CHANGE_EXAMPLE)
    private String passwordToChange;

    @ApiModelProperty(value = PASSWORD_TO_CHANGE_CONFIRM_VALUE, example = PASSWORD_TO_CHANGE_EXAMPLE)
    private String passwordToChangeConfirm;

    public PasswordUpdateRequest(String currentPassword, String accessToken,
                                 String passwordToChange, String passwordToChangeConfirm) {
        this.currentPassword = currentPassword;
        this.accessToken = accessToken;
        this.passwordToChange = passwordToChange;
        this.passwordToChangeConfirm = passwordToChangeConfirm;
    }
}
