package personal.jeuksipay.member.adapter.in.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import personal.jeuksipay.common.adapter.in.WebAdapter;
import personal.jeuksipay.member.adapter.in.web.mapper.MemberRequestToCommandMapper;
import personal.jeuksipay.member.adapter.in.web.request.MemberDeleteRequest;
import personal.jeuksipay.member.application.port.in.command.MemberDeleteCommand;
import personal.jeuksipay.member.application.port.in.usecase.DeleteMemberUseCase;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PRINCIPAL_POINTCUT;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class DeleteMemberController {
    private final DeleteMemberUseCase deleteMemberUseCase;

    private static final String DELETE_MEMBER = "회원 탈퇴";
    private static final String DELETE_MEMBER_DESCRIPTION
            = "비밀번호를 입력해 회원을 탈퇴할 수 있습니다.";
    private static final String DELETE_MEMBER_FORM = "회원 탈퇴 양식";

    @ApiOperation(value = DELETE_MEMBER, notes = DELETE_MEMBER_DESCRIPTION)
    @PreAuthorize(PRINCIPAL_POINTCUT)
    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(
            @RequestBody @ApiParam(value = DELETE_MEMBER_FORM) MemberDeleteRequest memberDeleteRequest) {
        MemberDeleteCommand memberDeleteCommand
                = MemberRequestToCommandMapper.mapToCommand(memberDeleteRequest);
        deleteMemberUseCase.deleteMember(memberDeleteCommand);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
