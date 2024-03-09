package personal.jeuksipay.member.adapter.in.web.response;

import lombok.Getter;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.wrapper.WrapperAccessor;

import java.util.List;

@Getter
public class GetMemberResponse {
    private final String email;
    private final String fullName;
    private final List<String> roleDescriptions;

    private GetMemberResponse(String email, String fullName, List<String> roleDescriptions) {
        this.email = email;
        this.fullName = fullName;
        this.roleDescriptions = roleDescriptions;
    }

    public static GetMemberResponse from(Member member) {
        String email = WrapperAccessor.getEmailValue(member.getEmail());
        String fullName = WrapperAccessor.getFullNameValue(member.getFullName());
        List<String> roleDescriptions = member.getRoles().toDescriptions();

        return new GetMemberResponse(email, fullName, roleDescriptions);
    }
}
