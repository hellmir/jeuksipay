package personal.jeuksipay.member.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.exception.general.DuplicateMemberException;
import personal.jeuksipay.member.domain.exception.general.DuplicateUsernameException;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.wrapper.Role;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static personal.jeuksipay.member.domain.exception.message.DuplicateExceptionMessage.*;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_BUSINESS_USER;
import static personal.jeuksipay.member.domain.wrapper.Role.ROLE_GENERAL_USER;
import static personal.jeuksipay.member.testutil.MemberTestConstant.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberPersistenceAdapterTest {
    @Autowired
    private MemberPersistenceAdapter memberPersistenceAdapter;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CryptoProvider cryptoProvider;

    @DisplayName("회원 JPA 엔티티를 생성하고 데이터를 암호화 해 저장할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER, ROLE_BUSINESS_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER, ROLE_GENERAL_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_GENERAL_USER, ROLE_ADMIN"
    })
    void saveMember(String email, String username, String password,
                    String fullName, String phone, Role role) {
        // given
        Member member = MemberTestObjectFactory.createMember(
                email, username, password, passwordEncoder, fullName, phone, List.of(role.toString())
        );

        // when
        memberPersistenceAdapter.saveMember(member);
        MemberJpaEntity memberJpaEntity = memberRepository.findById(member.getId()).get();

        // then
        assertThat(memberJpaEntity.getId()).isEqualTo(member.getId());
        assertThat(memberJpaEntity.getEmail()).isEqualTo(member.getEmail().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getUsername()).isEqualTo(member.getUsername().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getPassword()).isEqualTo(member.getPassword());
        assertThat(memberJpaEntity.getFullName()).isEqualTo(member.getFullName().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getPhone()).isEqualTo(member.getPhone().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getAddress()).isEqualTo(member.getAddress().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getRoles()).isEqualTo(member.getRoles());
        assertThat(memberJpaEntity.getCreatedAt()).isEqualTo(member.getCreatedAt());
        assertThat(memberJpaEntity.getModifiedAt()).isEqualTo(member.getModifiedAt());
    }

    @DisplayName("여러 권한을 가진 회원 JPA 엔티티를 생성하고 데이터를 암호화 해 저장할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER, ROLE_BUSINESS_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER, ROLE_GENERAL_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_GENERAL_USER, ROLE_ADMIN"
    })
    void saveMemberWithMultipleRoles(String email, String username, String password,
                                     String fullName, String phone, Role role1, Role role2) {
        // given
        Member member = MemberTestObjectFactory.createMember(
                email, username, password, passwordEncoder, fullName, phone, List.of(role1.toString(), role2.toString())
        );

        // when
        memberPersistenceAdapter.saveMember(member);
        MemberJpaEntity memberJpaEntity = memberRepository.findById(member.getId()).get();

        // then
        assertThat(memberJpaEntity.getId()).isEqualTo(member.getId());
        assertThat(memberJpaEntity.getEmail()).isEqualTo(member.getEmail().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getUsername()).isEqualTo(member.getUsername().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getPassword()).isEqualTo(member.getPassword());
        assertThat(memberJpaEntity.getFullName()).isEqualTo(member.getFullName().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getPhone()).isEqualTo(member.getPhone().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getAddress()).isEqualTo(member.getAddress().encrypt(cryptoProvider));
        assertThat(memberJpaEntity.getRoles()).isEqualTo(member.getRoles());
        assertThat(memberJpaEntity.getCreatedAt()).isEqualTo(member.getCreatedAt());
        assertThat(memberJpaEntity.getModifiedAt()).isEqualTo(member.getModifiedAt());
    }

    @DisplayName("중복된 사용자 이름을 전송하면 DuplicateUsernameException이 발생한다.")
    @Test
    void saveMemberByDuplicateUsername() {
        // given
        Member member1 = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        memberPersistenceAdapter.saveMember(member1);

        Member member2 = MemberTestObjectFactory.createMember(
                EMAIL2, USERNAME1, PASSWORD2, passwordEncoder, FULL_NAME2,
                PHONE2, List.of(ROLE_BUSINESS_USER.toString())
        );

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.saveMember(member2))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessage(DUPLICATE_USERNAME_EXCEPTION + USERNAME1);
    }

    @DisplayName("중복된 이메일을 전송하면 DuplicateMemberException이 발생한다.")
    @Test
    void saveMemberByDuplicateEmail() {
        Member member1 = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        memberPersistenceAdapter.saveMember(member1);

        Member member2 = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME2, PASSWORD2, passwordEncoder, FULL_NAME2,
                PHONE2, List.of(ROLE_BUSINESS_USER.toString())
        );

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.saveMember(member2))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage(DUPLICATE_EMAIL_EXCEPTION + EMAIL1);
    }

    @DisplayName("중복된 전화번호를 전송하면 DuplicateMemberException이 발생한다.")
    @Test
    void saveMemberByDuplicatePhone() {
        Member member1 = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        memberPersistenceAdapter.saveMember(member1);

        Member member2 = MemberTestObjectFactory.createMember(
                EMAIL2, USERNAME2, PASSWORD2, passwordEncoder, FULL_NAME2,
                PHONE1, List.of(ROLE_BUSINESS_USER.toString())
        );

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.saveMember(member2))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage(DUPLICATE_PHONE_EXCEPTION + PHONE1);
    }
}