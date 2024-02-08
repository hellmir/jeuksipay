package personal.jeuksipay.member.domain;


import lombok.Getter;

import java.util.List;

@Getter
public class RefreshToken {
    private String memberId;
    private List<String> roles;
    private String tokenValue;

    private RefreshToken(String memberId, List<String> roles, String tokenValue) {
        this.memberId = memberId;
        this.roles = roles;
        this.tokenValue = tokenValue;
    }

    public static RefreshToken of(String memberId, List<String> roles, String tokenValue) {
        return new RefreshToken(memberId, roles, tokenValue);
    }
}
