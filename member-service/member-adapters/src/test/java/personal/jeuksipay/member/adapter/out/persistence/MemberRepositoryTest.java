package personal.jeuksipay.member.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.wrapper.Email;
import personal.jeuksipay.member.domain.wrapper.Phone;
import personal.jeuksipay.member.domain.wrapper.Username;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CryptoProvider cryptoProvider;

    @DisplayName("암호화된 대상 이메일 주소의 존재 여부를 확인할 수 있다.")
    @Test
    void existsByEmail() {
        // given
        Email email1 = Email.of(EMAIL1);
        Email email2 = Email.of(EMAIL2);
        MemberJpaEntity memberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );

        memberJpaEntity.getEmail().encrypt(cryptoProvider);

        memberRepository.save(memberJpaEntity);

        // when
        boolean result1 = memberRepository.existsByEmail(email1);
        boolean result2 = memberRepository.existsByEmail(email2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("암호화된 대상 사용자 이름의 존재 여부를 확인할 수 있다.")
    @Test
    void existsByUsername() {
        // given
        Username username1 = Username.of(USERNAME1);
        Username username2 = Username.of(USERNAME2);
        MemberJpaEntity memberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );

        memberJpaEntity.getUsername().encrypt(cryptoProvider);

        memberRepository.save(memberJpaEntity);

        // when
        boolean result1 = memberRepository.existsByUsername(username1);
        boolean result2 = memberRepository.existsByUsername(username2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("암호화된 대상 전화번호의 존재 여부를 확인할 수 있다.")
    @Test
    void existsByPhone() {
        // given
        Phone phone1 = Phone.of(PHONE1);
        Phone phone2 = Phone.of(PHONE2);
        MemberJpaEntity memberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );

        memberJpaEntity.getPhone().encrypt(cryptoProvider);

        memberRepository.save(memberJpaEntity);

        // when
        boolean result1 = memberRepository.existsByPhone(phone1);
        boolean result2 = memberRepository.existsByPhone(phone2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
}