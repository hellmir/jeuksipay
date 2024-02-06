package personal.jeuksipay.member.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import personal.jeuksipay.common.adapter.out.PersistenceAdapter;
import personal.jeuksipay.member.application.port.out.SignUpPort;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.wrapper.Email;
import personal.jeuksipay.member.domain.wrapper.Phone;
import personal.jeuksipay.member.domain.wrapper.Username;


@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SignUpPort {
    private final MemberRepository memberRepository;
    private final CryptoProvider cryptoProvider;

    @Override
    public void saveMember(Member member) {
        MemberJpaEntity encryptedMemberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);

        Username encryptedUsername = encryptedMemberJpaEntity.getUsername();
        Email encryptedEmail = encryptedMemberJpaEntity.getEmail();
        Phone encryptedPhone = encryptedMemberJpaEntity.getPhone();
        validateNotDuplicateMember(encryptedUsername, encryptedEmail, encryptedPhone);

        memberRepository.save(encryptedMemberJpaEntity);

        member.setId(encryptedMemberJpaEntity.getId());
    }

    private void validateNotDuplicateMember(Username username, Email email, Phone phone) {
        checkUsername(username);
        checkEmail(email);
        checkPhone(phone);
    }

    private void checkUsername(Username username) {
        if (memberRepository.existsByUsername(username)) {
            username.throwDuplicateException(cryptoProvider);
        }
    }

    private void checkEmail(Email email) {
        if (memberRepository.existsByEmail(email)) {
            email.throwDuplicateException(cryptoProvider);
        }
    }

    private void checkPhone(Phone phone) {
        if (memberRepository.existsByPhone(phone)) {
            phone.throwDuplicateException(cryptoProvider);
        }
    }
}