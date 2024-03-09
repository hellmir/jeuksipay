package personal.jeuksipay.member.adapter.in.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.jeuksipay.common.adapter.in.InputFormatValidator;
import personal.jeuksipay.common.adapter.in.WebAdapter;
import personal.jeuksipay.member.adapter.in.web.response.GetMemberDetailsResponse;
import personal.jeuksipay.member.adapter.in.web.response.GetMemberResponse;
import personal.jeuksipay.member.application.port.in.usecase.GetMemberUseCase;
import personal.jeuksipay.member.domain.Member;

import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PRINCIPAL_POINTCUT;

@WebAdapter
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class GetMemberController {
    private final GetMemberUseCase getMemberUseCase;

    private static final String GET_USER_DETAILS = "회원 개인정보 조회";
    private static final String GET_USER_DETAILS_DESCRIPTION = "회원 개인정보를 조회할 수 있습니다.";

    private static final String GET_MEMBER = "회원 프로필 조회";
    private static final String GET_MEMBER_DESCRIPTION = "다른 회원의 정보를 조회할 수 있습니다.";
    public static final String MEMBER_ID = "회원 ID";

    @ApiOperation(value = GET_USER_DETAILS, notes = GET_USER_DETAILS_DESCRIPTION)
    @PreAuthorize(PRINCIPAL_POINTCUT)
    @GetMapping("/{memberId}/profile")
    public ResponseEntity<GetMemberDetailsResponse> getUserDetails
            (@PathVariable("memberId") @ApiParam(value = MEMBER_ID, example = ID_EXAMPLE) String memberId) {
        InputFormatValidator.validateId(memberId);
        Member member = getMemberUseCase.getMember(Long.parseLong(memberId));

        return ResponseEntity.status(HttpStatus.OK).body(GetMemberDetailsResponse.from(member));
    }

    @ApiOperation(value = GET_MEMBER, notes = GET_MEMBER_DESCRIPTION)
    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberResponse> getMember
            (@PathVariable("memberId") @ApiParam(value = MEMBER_ID, example = ID_EXAMPLE) String memberId) {
        InputFormatValidator.validateId(memberId);
        Member member = getMemberUseCase.getMember(Long.parseLong(memberId));

        return ResponseEntity.status(HttpStatus.OK).body(GetMemberResponse.from(member));
    }
}
