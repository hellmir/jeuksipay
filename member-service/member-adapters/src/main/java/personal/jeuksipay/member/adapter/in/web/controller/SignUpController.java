package personal.jeuksipay.member.adapter.in.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.jeuksipay.common.adapter.in.WebAdapter;
import personal.jeuksipay.member.adapter.in.web.mapper.MemberRequestMapper;
import personal.jeuksipay.member.adapter.in.web.request.SignUpRequest;
import personal.jeuksipay.member.adapter.in.web.response.SignUpResponse;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.application.port.in.usecase.SignUpUseCase;
import personal.jeuksipay.member.domain.Member;

import java.net.URI;

@WebAdapter
@RestController
@RequiredArgsConstructor
class SignUpController {
    private final SignUpUseCase signUpUseCase;

    private static final String SIGN_UP = "회원 가입";
    private static final String SIGN_UP_DESCRIPTION = "회원 가입 양식을 입력해 회원 가입을 할 수 있습니다.";
    private static final String SIGN_UP_FORM = "회원 가입 양식";

    @ApiOperation(value = SIGN_UP, notes = SIGN_UP_DESCRIPTION)
    @PostMapping("/members/signup")
    ResponseEntity<SignUpResponse> signUpMember
            (@RequestBody @ApiParam(value = SIGN_UP_FORM) SignUpRequest signUpRequest) {
        SignUpCommand signUpCommand = MemberRequestMapper.mapToCommand(signUpRequest);
        Member member = signUpUseCase.createMember(signUpCommand);

        return buildResponse(SignUpResponse.of(member.getId(), member.getCreatedAt()));
    }

    private ResponseEntity<SignUpResponse> buildResponse(SignUpResponse signUpResponse) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{memberId}")
                .buildAndExpand(signUpResponse.getMemberId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(signUpResponse);
    }
}
