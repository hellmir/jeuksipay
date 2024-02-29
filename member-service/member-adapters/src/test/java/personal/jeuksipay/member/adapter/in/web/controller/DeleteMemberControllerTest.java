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
import personal.jeuksipay.member.adapter.in.web.request.MemberDeleteRequest;
import personal.jeuksipay.member.adapter.in.web.security.CustomAuthenticationEntryPoint;
import personal.jeuksipay.member.adapter.out.security.JwtTokenProvider;
import personal.jeuksipay.member.application.port.in.usecase.DeleteMemberUseCase;
import personal.jeuksipay.member.application.port.in.usecase.UpdateMemberUseCase;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ActiveProfiles("test")
@WebMvcTest(controllers = DeleteMemberController.class)
class DeleteMemberControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private DeleteMemberUseCase deleteMemberUseCase;

        @MockBean
        private JwtTokenProvider jwtTokenProvider;

        @MockBean
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @DisplayName("비밀번호를 입력해 회원 탈퇴할 수 있다.")
    @Test
    @WithMockUser
    void deleteMember() throws Exception {
        // given
        MemberDeleteRequest memberDeleteRequest = new MemberDeleteRequest(PASSWORD1, TOKEN_VALUE1);

        // when, then
        mockMvc.perform(delete("/members")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(memberDeleteRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}