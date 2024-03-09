package personal.jeuksipay.member.adapter.out.persistence.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.wrapper.Email;
import personal.jeuksipay.member.domain.wrapper.Phone;
import personal.jeuksipay.member.domain.wrapper.Username;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;
import java.util.Optional;

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
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );

        memberRepository.save(memberJpaEntity);

        // when
        boolean result1 = memberRepository.existsByEmail(email1.encrypt(cryptoProvider));
        boolean result2 = memberRepository.existsByEmail(email2.encrypt(cryptoProvider));

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
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );

        memberRepository.save(memberJpaEntity);

        // when
        boolean result1 = memberRepository.existsByUsername(username1.encrypt(cryptoProvider));
        boolean result2 = memberRepository.existsByUsername(username2.encrypt(cryptoProvider));

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
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );

        memberRepository.save(memberJpaEntity);

        // when
        boolean result1 = memberRepository.existsByPhone(phone1.encrypt(cryptoProvider));
        boolean result2 = memberRepository.existsByPhone(phone2.encrypt(cryptoProvider));

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("저장된 이메일 주소를 통해 회원을 찾을 수 있다.")
    @Test
    void findByEmailByExistent() {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        MemberJpaEntity savedMemberJpaEntity = memberRepository.save(memberJpaEntity);

        // when
        Optional<MemberJpaEntity> foundMemberJpaEntity
                = memberRepository.findByEmail(Email.of(EMAIL1).encrypt(cryptoProvider));

        // then
        assertThat(foundMemberJpaEntity.get().getId()).isEqualTo(savedMemberJpaEntity.getId());
        assertThat(foundMemberJpaEntity.get().getEmail().decrypt(cryptoProvider)).isEqualTo(Email.of(EMAIL1));
        assertThat(foundMemberJpaEntity.get().getPassword().matchOriginalPassword(passwordEncoder, PASSWORD1)).isTrue();
    }

    @DisplayName("저장되지 않은 이메일 주소로 회원을 검색하면 회원을 반환하지 않는다.")
    @Test
    void findByEmailByNotExistent() {
        // given, when
        Optional<MemberJpaEntity> foundMemberJpaEntity
                = memberRepository.findByEmail(Email.of(EMAIL1).encrypt(cryptoProvider));

        // then
        assertThat(foundMemberJpaEntity).isEmpty();
    }

    @DisplayName("저장된 사용자 이름을 통해 회원을 찾을 수 있다.")
    @Test
    void findByUsernameByExistent() {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1, PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        MemberJpaEntity savedMemberJpaEntity = memberRepository.save(memberJpaEntity);


        // when
        Optional<MemberJpaEntity> foundMemberJpaEntity
                = memberRepository.findByUsername(Username.of(USERNAME1).encrypt(cryptoProvider));

        // then
        assertThat(foundMemberJpaEntity.get().getId()).isEqualTo(savedMemberJpaEntity.getId());
        assertThat(foundMemberJpaEntity.get().getUsername().decrypt(cryptoProvider)).isEqualTo(Username.of(USERNAME1));
        assertThat(foundMemberJpaEntity.get().getPassword().matchOriginalPassword(passwordEncoder, PASSWORD1)).isTrue();
    }

    @DisplayName("저장되지 않은 사용자 이름으로 회원을 검색하면 회원을 반환하지 않는다.")
    @Test
    void findByUsernameByNotExistent() {
        // given, when
        Optional<MemberJpaEntity> foundMemberJpaEntity
                = memberRepository.findByUsername(Username.of(USERNAME1).encrypt(cryptoProvider));

        // then
        assertThat(foundMemberJpaEntity).isEmpty();
    }
}
