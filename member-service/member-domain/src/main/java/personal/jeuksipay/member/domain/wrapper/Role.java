package personal.jeuksipay.member.domain.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN("관리자"),
    ROLE_BUSINESS_USER("기업 고객"),
    ROLE_GENERAL_USER("일반 고객");

    private final String description;

    public boolean isBusinessUser() {
        return this == ROLE_BUSINESS_USER;
    }

    public boolean isGeneralUser() {
        return this == ROLE_GENERAL_USER;
    }
}
