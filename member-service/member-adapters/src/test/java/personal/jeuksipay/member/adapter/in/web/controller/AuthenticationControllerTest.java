package personal.jeuksipay.member.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import personal.jeuksipay.member.adapter.in.web.request.SignInRequest;
import personal.jeuksipay.member.adapter.in.web.security.CustomAuthenticationEntryPoint;
import personal.jeuksipay.member.adapter.out.security.JwtTokenProvider;
import personal.jeuksipay.member.application.port.in.AuthenticationResult;
import personal.jeuksipay.member.application.port.in.command.signInCommand;
import personal.jeuksipay.member.application.port.in.usecase.AuthenticationUseCase;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.wrapper.Roles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationUseCase authenticationUseCase;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @DisplayName("올바른 로그인 양식을 입력하면 로그인을 할 수 있다.")
    @Test
    @WithMockUser
    void signInMember() throws Exception {
        // given
        SignInRequest signInRequest = new SignInRequest(EMAIL1, PASSWORD1);

        Member member = mock(Member.class);
        when(member.getRoles()).thenReturn(Roles.from(List.of(ROLE_GENERAL_USER.toString())));
        AuthenticationResult authenticationResult = AuthenticationResult.from(member, "accessToken", "refreshToken");

        when(authenticationUseCase.signInMember(any(signInCommand.class))).thenReturn(authenticationResult);

        // when, then
        mockMvc.perform(post("/members/login")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(signInRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("올바른 리프레시 토큰 값을 입력하면 새로운 엑세스 토큰을 발급할 수 있다.")
    @Test
    @WithMockUser
    void issueNewAccessToken() throws Exception {
        // given
        when(authenticationUseCase.issueNewAccessToken(TOKEN_VALUE1)).thenReturn(TOKEN_VALUE2);

        // when, then
        mockMvc.perform(post("/members/access-token")
                        .queryParam("refreshToken", TOKEN_VALUE1)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
