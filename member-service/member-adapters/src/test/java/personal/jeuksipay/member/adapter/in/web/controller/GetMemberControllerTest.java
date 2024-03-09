package personal.jeuksipay.member.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import personal.jeuksipay.member.adapter.in.web.security.CustomAuthenticationEntryPoint;
import personal.jeuksipay.member.adapter.out.security.JwtTokenProvider;
import personal.jeuksipay.member.application.port.in.usecase.GetMemberUseCase;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static personal.jeuksipay.common.adapter.in.ApiConstant.ID_EXAMPLE;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;


@ActiveProfiles("test")
@WebMvcTest(controllers = GetMemberController.class)
class GetMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetMemberUseCase getMemberUseCase;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @DisplayName("회원 ID를 입력해 자신의 개인정보를 조회할 수 있다.")
    @Test
    @WithMockUser
    void getMemberDetails() throws Exception {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );

        when(getMemberUseCase.getMember(Long.parseLong(ID_EXAMPLE))).thenReturn(member);

        // when, then
        mockMvc.perform(get("/members/{memberId}/profile", ID_EXAMPLE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원 ID를 입력해 다른 회원의 정보를 조회할 수 있다.")
    @Test
    @WithMockUser
    void getMember() throws Exception {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );

        when(getMemberUseCase.getMember(Long.parseLong(ID_EXAMPLE))).thenReturn(member);

        // when, then
        mockMvc.perform(get("/members/{memberId}", ID_EXAMPLE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
