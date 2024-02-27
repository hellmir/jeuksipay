package personal.jeuksipay.member.adapter.in.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.jeuksipay.common.adapter.in.WebAdapter;
import personal.jeuksipay.member.adapter.in.web.container.AddressRequest;
import personal.jeuksipay.member.adapter.in.web.mapper.MemberRequestToCommandMapper;
import personal.jeuksipay.member.adapter.in.web.request.EmailUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.request.PasswordUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.request.PhoneUpdateRequest;
import personal.jeuksipay.member.application.port.in.command.AddressCommand;
import personal.jeuksipay.member.application.port.in.command.EmailUpdateCommand;
import personal.jeuksipay.member.application.port.in.command.PasswordUpdateCommand;
import personal.jeuksipay.member.application.port.in.command.PhoneUpdateCommand;
import personal.jeuksipay.member.application.port.in.usecase.UpdateMemberUseCase;

import static personal.jeuksipay.member.adapter.in.web.ApiConstant.PRINCIPAL_POINTCUT;

@WebAdapter
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class UpdateMemberController {
    private final UpdateMemberUseCase updateMemberUseCase;

    private static final String UPDATE_ADDRESS = "주소 변경";
    private static final String UPDATE_ADDRESS_DESCRIPTION = "변경할 주소를 입력해 주소를 수정할 수 있습니다.";
    private static final String UPDATE_ADDRESS_FORM = "주소 변경 양식";

    private static final String UPDATE_EMAIL = "이메일 주소 변경";
    private static final String UPDATE_UPDATE_EMAIL_DESCRIPTION
            = "비밀번호와 변경할 이메일 주소를 입력해 이메일 주소를 수정할 수 있습니다.";
    private static final String UPDATE_UPDATE_EMAIL_FORM = "이메일 주소 변경 양식";

    private static final String UPDATE_PHONE = "전화번호 변경";
    private static final String UPDATE_PHONE_DESCRIPTION = "비밀번호와 변경할 전화번호를 입력해 전화번호를 수정할 수 있습니다.";
    private static final String UPDATE_PHONE_FORM = "전화번호 변경 양식";

    private static final String UPDATE_PASSWORD = "비밀번호 변경";
    private static final String UPDATE_PASSWORD_DESCRIPTION
            = "현재 비밀번호와 변경할 비밀번호를 입력해 비밀번호를 수정할 수 있습니다.";
    private static final String UPDATE_PASSWORD_FORM = "비밀번호 변경 양식";

    @ApiOperation(value = UPDATE_ADDRESS, notes = UPDATE_ADDRESS_DESCRIPTION)
    @PreAuthorize(PRINCIPAL_POINTCUT)
    @PatchMapping("/address")
    public ResponseEntity<Void> updateAddress
            (@RequestBody @ApiParam(value = UPDATE_ADDRESS_FORM) AddressRequest addressRequest) {
        AddressCommand addressCommand = MemberRequestToCommandMapper.mapToAddressCommand(addressRequest);
        updateMemberUseCase.updateAddress(addressCommand);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = UPDATE_EMAIL, notes = UPDATE_UPDATE_EMAIL_DESCRIPTION)
    @PreAuthorize(PRINCIPAL_POINTCUT)
    @PatchMapping("/email")
    public ResponseEntity<Void> updateEmail
            (@RequestBody @ApiParam(value = UPDATE_UPDATE_EMAIL_FORM) EmailUpdateRequest emailUpdateRequest) {
        EmailUpdateCommand emailUpdateCommand = MemberRequestToCommandMapper.mapToCommand(emailUpdateRequest);
        updateMemberUseCase.updateEmail(emailUpdateCommand);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = UPDATE_PHONE, notes = UPDATE_PHONE_DESCRIPTION)
    @PreAuthorize(PRINCIPAL_POINTCUT)
    @PatchMapping("/phone")
    public ResponseEntity<Void> updatePhone
            (@RequestBody @ApiParam(value = UPDATE_PHONE_FORM) PhoneUpdateRequest phoneUpdateRequest) {
        PhoneUpdateCommand phoneUpdateCommand = MemberRequestToCommandMapper.mapToCommand(phoneUpdateRequest);
        updateMemberUseCase.updatePhone(phoneUpdateCommand);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = UPDATE_PASSWORD, notes = UPDATE_PASSWORD_DESCRIPTION)
    @PreAuthorize(PRINCIPAL_POINTCUT)
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword
            (@RequestBody @ApiParam(value = UPDATE_PASSWORD_FORM) PasswordUpdateRequest passwordUpdateRequest) {
        PasswordUpdateCommand passwordUpdateCommand = MemberRequestToCommandMapper.mapToCommand(passwordUpdateRequest);
        updateMemberUseCase.updatePassword(passwordUpdateCommand);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
