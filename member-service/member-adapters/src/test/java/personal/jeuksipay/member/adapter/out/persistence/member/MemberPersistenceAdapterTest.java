package personal.jeuksipay.member.adapter.out.persistence.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.exception.general.DuplicateMemberException;
import personal.jeuksipay.member.domain.exception.general.DuplicateUsernameException;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.wrapper.*;
import personal.jeuksipay.member.testutil.MemberTestObjectFactory;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static personal.jeuksipay.member.domain.exception.message.DuplicateExceptionMessage.*;
import static personal.jeuksipay.member.domain.exception.message.NotFoundExceptionMessage.*;
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

    @DisplayName("회원 ID를 통해 회원을 조회할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_ADMIN"
    })
    void findMemberById(String email, String username, String password,
                        String fullName, String phone, String role) {
        // given
        Member createdMember = MemberTestObjectFactory.createMember(
                email, username, password, passwordEncoder, fullName, phone, List.of(role)
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(createdMember, cryptoProvider);

        memberRepository.save(memberJpaEntity);

        // when
        Member foundMember = memberPersistenceAdapter.findMemberById(memberJpaEntity.getId());

        // then
        assertThat(foundMember.getEmail()).isEqualTo(Email.of(email));
        assertThat(foundMember.getUsername()).isEqualTo(Username.of(username));
        assertThat(foundMember.getEmail()).isEqualTo(Email.of(email));
        assertThat(foundMember.getUsername()).isEqualTo(Username.of(username));
    }

    @DisplayName("잘못된 회원 ID로 회원을 조회하면 EntityNotFoundException이 발생한다.")
    @Test
    void findMemberByWrongId() {
        // given
        MemberJpaEntity memberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );
        memberRepository.save(memberJpaEntity);

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.findMemberById(memberJpaEntity.getId() + 1))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(MEMEBER_ID_NOT_FOUND_EXCEPTION + (memberJpaEntity.getId() + 1));
    }

    @DisplayName("이메일 주소 또는 사용자 이름을 통해 회원을 조회하고 최종 로그인 시간을 기록할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_ADMIN"
    })
    void findMemberByEmailOrUsername(String email, String username, String password,
                                     String fullName, String phone, String role) {
        // given
        Member createdMember = MemberTestObjectFactory.createMember(
                email, username, password, passwordEncoder, fullName, phone, List.of(role)
        );
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(createdMember, cryptoProvider);

        memberRepository.save(memberJpaEntity);

        LocalDateTime beforeLogin = LocalDateTime.now().minusNanos(1);

        // when
        Member foundMember1 = memberPersistenceAdapter.findMemberByEmailOrUsername(email);
        Member foundMember2 = memberPersistenceAdapter.findMemberByEmailOrUsername(username);

        // then
        LocalDateTime afterLogin = LocalDateTime.now().plusNanos(1);

        assertThat(foundMember1.getEmail()).isEqualTo(Email.of(email));
        assertThat(foundMember1.getUsername()).isEqualTo(Username.of(username));
        assertThat(foundMember2.getEmail()).isEqualTo(Email.of(email));
        assertThat(foundMember2.getUsername()).isEqualTo(Username.of(username));

        assertThat(memberJpaEntity.getLastLoggedInAt()).isAfter(beforeLogin);
        assertThat(memberJpaEntity.getLastLoggedInAt()).isBefore(afterLogin);
        assertThat(foundMember2.getLastLoggedInAt()).isEqualTo(memberJpaEntity.getLastLoggedInAt());
    }

    @DisplayName("존재하지 않는 이메일 주소를 통해 회원을 조회하려 하면 EMAIL_NOT_FOUND_EXCEPTION이 발생한다.")
    @Test
    void findMemberByNonExistentEmail() {
        // given
        MemberJpaEntity memberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );
        memberRepository.save(memberJpaEntity);

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.findMemberByEmailOrUsername(EMAIL2))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(EMAIL_NOT_FOUND_EXCEPTION + EMAIL2);
    }

    @DisplayName("존재하지 않는 사용자 이름을 통해 회원을 조회하려 하면 USERNAME_NOT_FOUND_EXCEPTION이 발생한다.")
    @Test
    void findMemberByNonExistentUsername() {
        // given
        MemberJpaEntity memberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );
        memberRepository.save(memberJpaEntity);

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.findMemberByEmailOrUsername(USERNAME2))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(USERNAME_NOT_FOUND_EXCEPTION + USERNAME2);
    }

    @DisplayName("중복된 사용자 이름을 전송하면 DuplicateUsernameException이 발생한다.")
    @Test
    void checkDuplicateUsername() {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        memberPersistenceAdapter.saveMember(member);

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.checkDuplicateUsername(USERNAME1))
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessage(DUPLICATE_USERNAME_EXCEPTION + USERNAME1);
    }

    @DisplayName("중복된 이메일을 전송하면 DuplicateMemberException이 발생한다.")
    @Test
    void checkDuplicateEmail() {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        memberPersistenceAdapter.saveMember(member);

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.checkDuplicateEmail(EMAIL1))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage(DUPLICATE_EMAIL_EXCEPTION + EMAIL1);
    }

    @DisplayName("중복된 전화번호를 전송하면 DuplicateMemberException이 발생한다.")
    @Test
    void checkDuplicatePhone() {
        // given
        Member member = MemberTestObjectFactory.createMember(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString())
        );
        memberPersistenceAdapter.saveMember(member);

        // when, then
        assertThatThrownBy(() -> memberPersistenceAdapter.checkDuplicatePhone(PHONE1))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage(DUPLICATE_PHONE_EXCEPTION + PHONE1);
    }


    @DisplayName("회원 정보를 수정할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "abcd@abc.com, person1, Abcd1234!, 홍길동, 01012345678, ROLE_GENERAL_USER, ROLE_BUSINESS_USER",
            "abcd@abcd.com, person2, Abcd12345!, 고길동, 01012345679, ROLE_BUSINESS_USER, ROLE_GENERAL_USER",
            "abcd@abcde.com, person3, Abcd123456!, 김길동, 01012345680, ROLE_GENERAL_USER, ROLE_ADMIN"
    })
    void updateMember(String email, String username, String password,
                      String fullName, String phone, Role role) {
        // given
        MemberJpaEntity createdMemberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );
        memberRepository.save(createdMemberJpaEntity);

        Member member = MemberTestObjectFactory.createMember(
                createdMemberJpaEntity.getId().toString(), email, username,
                password, passwordEncoder, fullName, phone, List.of(role.toString())
        );

        // when
        memberPersistenceAdapter.updateMember(member);
        MemberJpaEntity foundMemberJpaEntity = memberRepository.findById(createdMemberJpaEntity.getId()).get();

        // then
        assertThat(foundMemberJpaEntity.getEmail().decrypt(cryptoProvider)).isEqualTo(Email.of(email));
        assertThat(foundMemberJpaEntity.getUsername().decrypt(cryptoProvider)).isEqualTo(Username.of(username));
        assertThat(foundMemberJpaEntity.getPassword().matchOriginalPassword(passwordEncoder, password)).isTrue();
        assertThat(foundMemberJpaEntity.getFullName().decrypt(cryptoProvider)).isEqualTo(FullName.of(fullName));
        assertThat(foundMemberJpaEntity.getPhone().decrypt(cryptoProvider)).isEqualTo(Phone.of(phone));
        assertThat(foundMemberJpaEntity.getAddress().decrypt(cryptoProvider))
                .isEqualTo(Address.of(CITY, STREET, ZIPCODE, DETAILED_ADDRESS));
        assertThat(foundMemberJpaEntity.getRoles()).isEqualTo(createdMemberJpaEntity.getRoles());
    }

    @DisplayName("회원 데이터를 삭제할 수 있다.")
    @Test
    void deleteMember() {
        // given
        MemberJpaEntity createdMemberJpaEntity = MemberTestObjectFactory.createMemberJpaEntity(
                EMAIL1, USERNAME1, PASSWORD1, passwordEncoder, FULL_NAME1,
                PHONE1, List.of(ROLE_GENERAL_USER.toString()), cryptoProvider
        );
        memberRepository.save(createdMemberJpaEntity);

        // when
        memberPersistenceAdapter.deleteMember(createdMemberJpaEntity.getId());

        // then
        memberRepository.findById(createdMemberJpaEntity.getId()).isEmpty();
    }
}
