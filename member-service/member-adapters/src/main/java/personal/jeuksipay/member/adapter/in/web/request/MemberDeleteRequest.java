package personal.jeuksipay.member.adapter.in.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberDeleteRequest {
    @ApiModelProperty(value = PASSWORD_VALUE, example = PASSWORD_EXAMPLE)
    private String password;

    @ApiModelProperty(value = ACCESS_TOKEN_VALUE, example = ACCESS_TOKEN_EXAMPLE)
    private String accessToken;
}
