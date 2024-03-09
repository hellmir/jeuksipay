package personal.jeuksipay.member.domain;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(memberId, that.memberId) && new ArrayList<>(roles).equals(new ArrayList<>(that.roles))
                && Objects.equals(tokenValue, that.tokenValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, roles, tokenValue);
    }
}
