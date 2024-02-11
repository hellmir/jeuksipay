package personal.jeuksipay.member.adapter.in.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.wrapper.WrapperAccessor;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class GetMemberDetailsResponse {
    private final String email;
    private final String username;
    private final String fullName;
    private final String phone;
    private final AddressResponse addressResponse;
    private final List<String> roleDescriptions;

    public static GetMemberDetailsResponse from(Member member) {
        return GetMemberDetailsResponse.builder()
                .email(WrapperAccessor.getEmailValue(member.getEmail()))
                .username(WrapperAccessor.getUsernameValue(member.getUsername()))
                .fullName(WrapperAccessor.getFullNameValue(member.getFullName()))
                .phone(WrapperAccessor.getPhoneValue(member.getPhone()))
                .addressResponse(AddressResponse.from(member.getAddress()))
                .roleDescriptions(member.getRoles().toDescriptions())
                .build();
    }
}
