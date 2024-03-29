package personal.jeuksipay.member.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import personal.jeuksipay.member.adapter.in.web.request.EmailUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.request.PasswordUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.request.PhoneUpdateRequest;
import personal.jeuksipay.member.adapter.in.web.security.CustomAuthenticationEntryPoint;
import personal.jeuksipay.member.adapter.out.security.JwtTokenProvider;
import personal.jeuksipay.member.application.port.in.usecase.UpdateMemberUseCase;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = UpdateMemberController.class)
class UpdateMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UpdateMemberUseCase updateMemberUseCase;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @DisplayName("변경할 주소를 입력해 주소를 변경할 수 있다.")
    @Test
    @WithMockUser
    void updateAddress() throws Exception {
        // given
        EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest(PASSWORD1, TOKEN_VALUE1, EMAIL1);

        // when, then
        mockMvc.perform(patch("/members/address")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(emailUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("비밀번호와 변경할 이메일 주소를 입력해 이메일 주소를 변경할 수 있다.")
    @Test
    @WithMockUser
    void updateEmail() throws Exception {
        // given
        EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest(PASSWORD1, TOKEN_VALUE1, EMAIL1);

        // when, then
        mockMvc.perform(patch("/members/email")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(emailUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("비밀번호와 변경할 전화번호를 입력해 전화번호를 변경할 수 있다.")
    @Test
    @WithMockUser
    void updatePhone() throws Exception {
        // given
        PhoneUpdateRequest phoneUpdateRequest = new PhoneUpdateRequest(PASSWORD1, TOKEN_VALUE1, EMAIL1);

        // when, then
        mockMvc.perform(patch("/members/phone")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(phoneUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("현재 비밀번호와 변경할 비밀번호, 비밀번호 확인을 입력해 비밀번호를 변경할 수 있다.")
    @Test
    @WithMockUser
    void updatePassword() throws Exception {
        // given
        PasswordUpdateRequest passwordUpdateRequest
                = new PasswordUpdateRequest(PASSWORD1, TOKEN_VALUE1, PASSWORD2, PASSWORD2);

        // when, then
        mockMvc.perform(patch("/members/password")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(passwordUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
