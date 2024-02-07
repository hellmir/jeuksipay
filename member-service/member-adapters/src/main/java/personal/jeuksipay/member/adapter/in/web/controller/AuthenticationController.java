package personal.jeuksipay.member.adapter.in.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.jeuksipay.common.adapter.in.WebAdapter;
import personal.jeuksipay.member.adapter.in.web.mapper.MemberRequestToCommandMapper;
import personal.jeuksipay.member.adapter.in.web.request.SignInRequest;
import personal.jeuksipay.member.adapter.in.web.response.SignInResponse;
import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.signInCommand;
import personal.jeuksipay.member.application.port.in.usecase.AuthenticationUseCase;

@WebAdapter
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationUseCase authenticationUseCase;

    private static final String SIGN_IN = "로그인";
    private static final String SIGN_IN_DESCRIPTION = "email 또는 username과 비밀번호를 입력해 로그인을 할 수 있습니다.";
    private static final String SIGN_IN_FORM = "로그인 양식";

    @ApiOperation(value = SIGN_IN, notes = SIGN_IN_DESCRIPTION)
    @PostMapping("/login")
    public ResponseEntity<SignInResponse> signInMember
            (@RequestBody @ApiParam(value = SIGN_IN_FORM) SignInRequest signInRequest) {
        signInCommand signInCommand = MemberRequestToCommandMapper.mapToCommand(signInRequest);
        AuthenticationResult authenticationResult = authenticationUseCase.signInMember(signInCommand);

        return ResponseEntity.status(HttpStatus.OK).body(SignInResponse.from(authenticationResult));
    }
}
