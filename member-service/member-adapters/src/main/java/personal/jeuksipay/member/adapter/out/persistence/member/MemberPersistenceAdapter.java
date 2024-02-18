package personal.jeuksipay.member.adapter.out.persistence.member;

import lombok.RequiredArgsConstructor;
import personal.jeuksipay.common.adapter.out.PersistenceAdapter;
import personal.jeuksipay.member.adapter.out.mapper.MemberJpaEntityToDomainMapper;
import personal.jeuksipay.member.application.port.out.FindMemberPort;
import personal.jeuksipay.member.application.port.out.SignUpPort;
import personal.jeuksipay.member.application.port.out.UpdateMemberPort;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.exception.general.DuplicateMemberException;
import personal.jeuksipay.member.domain.exception.general.DuplicateUsernameException;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.wrapper.Email;
import personal.jeuksipay.member.domain.wrapper.Phone;
import personal.jeuksipay.member.domain.wrapper.Username;

import javax.persistence.EntityNotFoundException;

import static personal.jeuksipay.member.domain.exception.message.DuplicateExceptionMessage.*;
import static personal.jeuksipay.member.domain.exception.message.NotFoundExceptionMessage.*;


@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements SignUpPort, FindMemberPort, UpdateMemberPort {
    private final MemberRepository memberRepository;
    private final CryptoProvider cryptoProvider;

    @Override
    public void saveMember(Member member) {
        MemberJpaEntity encryptedMemberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        memberRepository.save(encryptedMemberJpaEntity);

        member.setId(encryptedMemberJpaEntity.getId());
    }

    @Override
    public Member findMemberById(Long memberId) {
        MemberJpaEntity memberJpaEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMEBER_ID_NOT_FOUND_EXCEPTION + memberId));

        return MemberJpaEntityToDomainMapper.mapToDomainEntity(memberJpaEntity, cryptoProvider);
    }

    @Override
    public Member findMemberByEmailOrUsername(String emailOrUsername) {
        MemberJpaEntity memberJpaEntity = emailOrUsername.contains("@")
                ? memberRepository.findByEmail(Email.of(emailOrUsername).encrypt(cryptoProvider))
                .orElseThrow(() -> new EntityNotFoundException
                        (EMAIL_NOT_FOUND_EXCEPTION + emailOrUsername))
                : memberRepository.findByUsername(Username.of(emailOrUsername).encrypt(cryptoProvider))
                .orElseThrow(() -> new EntityNotFoundException
                        (USERNAME_NOT_FOUND_EXCEPTION + emailOrUsername));

        memberJpaEntity.updateLoginTime();

        return MemberJpaEntityToDomainMapper.mapToDomainEntity(memberJpaEntity, cryptoProvider);
    }

    @Override
    public void checkDuplicateUsername(String username) {
        if (memberRepository.existsByUsername(Username.of(username).encrypt(cryptoProvider))) {
            throw new DuplicateUsernameException(DUPLICATE_USERNAME_EXCEPTION + username);
        }
    }

    @Override
    public void checkDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(Email.of(email).encrypt(cryptoProvider))) {
            throw new DuplicateMemberException(DUPLICATE_EMAIL_EXCEPTION + email);
        }
    }

    @Override
    public void checkDuplicatePhone(String phone) {
        if (memberRepository.existsByPhone(Phone.of(phone).encrypt(cryptoProvider))) {
            throw new DuplicateMemberException(DUPLICATE_PHONE_EXCEPTION + phone);
        }
    }

    @Override
    public void updateMember(Member member) {
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.from(member, cryptoProvider);
        memberRepository.save(memberJpaEntity);
    }
}
