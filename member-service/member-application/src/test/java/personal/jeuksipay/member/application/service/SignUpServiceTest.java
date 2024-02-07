package personal.jeuksipay.member.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.application.port.in.command.SignUpCommand;
import personal.jeuksipay.member.application.port.out.SignUpPort;
import personal.jeuksipay.member.application.validation.PasswordValidator;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {
    @InjectMocks
    private SignUpService signUpService;

    @Mock
    private SignUpPort signUpPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordValidator passwordValidator;

    @DisplayName("올바른 회원 가입 양식을 전송하면 회원 도메인 엔티티를 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER, ROLE_BUSINESS_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER, ROLE_GENERAL_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_GENERAL_USER, ROLE_ADMIN"
    })
    void createMember(String email, String username, String password,
                      String fullName, String phone, Role role) {
        // given
        SignUpCommand signUpCommand = MemberTestObjectFactory.createSignUpCommand(
                email, username, password, password, fullName, phone, List.of(role.toString())
        );

        LocalDateTime beforeSignUp = LocalDateTime.now().minusNanos(1);

        // when
        Member member = signUpService.createMember(signUpCommand);

        // then
        LocalDateTime afterSignUp = LocalDateTime.now().plusNanos(1);

        assertThat(member.getEmail()).isEqualTo(Email.of(signUpCommand.getEmail()));
        assertThat(member.getUsername()).isEqualTo(Username.of(signUpCommand.getUsername()));
        assertThat(member.getPassword()).isEqualTo(Password.from(signUpCommand.getPassword(), passwordEncoder));
        assertThat(member.getFullName()).isEqualTo(FullName.of(signUpCommand.getFullName()));
        assertThat(member.getAddress()).isEqualTo(Address.of(CITY, STREET, ZIPCODE, DETAILED_ADDRESS));
        assertThat(member.getPhone()).isEqualTo(Phone.of(signUpCommand.getPhone()));
        assertThat(member.getRoles()).isEqualTo(Roles.from(signUpCommand.getRoles()));

        assertThat(member.getCreatedAt()).isAfter(beforeSignUp);
        assertThat(member.getCreatedAt()).isBefore(afterSignUp);
        assertThat(member.getModifiedAt()).isAfter(beforeSignUp);
        assertThat(member.getModifiedAt()).isBefore(afterSignUp);
    }


    @DisplayName("여러 권한을 가진 회원 도메인 엔티티를 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER, ROLE_BUSINESS_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER, ROLE_GENERAL_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_GENERAL_USER, ROLE_ADMIN"
    })
    void createMemberWithMultipleRoles(String email, String username, String password, String fullName,
                                       String phone, Role role1, Role role2) {
        // given
        SignUpCommand signUpCommand = MemberTestObjectFactory.createSignUpCommand(
                email, username, password, password, fullName, phone, List.of(role1.toString(), role2.toString())
        );

        doNothing().when(signUpPort).saveMember(any(Member.class));

        LocalDateTime beforeSignUp = LocalDateTime.now().minusNanos(1);

        // when
        Member member = signUpService.createMember(signUpCommand);

        // then
        LocalDateTime afterSignUp = LocalDateTime.now().plusNanos(1);

        assertThat(member.getEmail()).isEqualTo(Email.of(signUpCommand.getEmail()));
        assertThat(member.getUsername()).isEqualTo(Username.of(signUpCommand.getUsername()));
        assertThat(member.getPassword()).isEqualTo(Password.from(signUpCommand.getPassword(), passwordEncoder));
        assertThat(member.getFullName()).isEqualTo(FullName.of(signUpCommand.getFullName()));
        assertThat(member.getAddress()).isEqualTo(Address.of(CITY, STREET, ZIPCODE, DETAILED_ADDRESS));
        assertThat(member.getPhone()).isEqualTo(Phone.of(signUpCommand.getPhone()));
        assertThat(member.getRoles()).isEqualTo(Roles.from(signUpCommand.getRoles()));

        assertThat(member.getCreatedAt()).isAfter(beforeSignUp);
        assertThat(member.getCreatedAt()).isBefore(afterSignUp);
        assertThat(member.getModifiedAt()).isAfter(beforeSignUp);
        assertThat(member.getModifiedAt()).isBefore(afterSignUp);
    }
}