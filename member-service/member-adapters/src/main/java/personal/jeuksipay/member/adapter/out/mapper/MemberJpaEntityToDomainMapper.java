package personal.jeuksipay.member.adapter.out.mapper;

import personal.jeuksipay.member.adapter.out.persistence.member.MemberJpaEntity;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.CryptoProvider;

public class MemberJpaEntityToDomainMapper {

    public static Member mapToDomainEntity(MemberJpaEntity memberJpaEntity, CryptoProvider cryptoProvider) {
        return Member.builder()
                .id(memberJpaEntity.getId())
                .email(memberJpaEntity.getEmail().decrypt(cryptoProvider))
                .username(memberJpaEntity.getUsername().decrypt(cryptoProvider))
                .password(memberJpaEntity.getPassword())
                .fullName(memberJpaEntity.getFullName().decrypt(cryptoProvider))
                .phone(memberJpaEntity.getPhone().decrypt(cryptoProvider))
                .address(memberJpaEntity.getAddress().decrypt(cryptoProvider))
                .roles(memberJpaEntity.getRoles())
                .createdAt(memberJpaEntity.getCreatedAt())
                .modifiedAt(memberJpaEntity.getModifiedAt())
                .lastLoggedInAt(memberJpaEntity.getLastLoggedInAt())
                .build();
    }
}
