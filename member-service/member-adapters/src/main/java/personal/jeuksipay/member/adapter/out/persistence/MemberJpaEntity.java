package personal.jeuksipay.member.adapter.out.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.jeuksipay.member.domain.Address;
import personal.jeuksipay.member.domain.Member;
import personal.jeuksipay.member.domain.security.CryptoProvider;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "member")
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Convert(converter = Email.EmailConverter.class)
    @Column(nullable = false, unique = true, length = 60)
    @JsonIgnore
    private Email email;

    @Convert(converter = Username.UsernameConverter.class)
    @Column(nullable = false, unique = true, length = 50)
    private Username username;

    @Convert(converter = Password.PasswordConverter.class)
    @Column(nullable = false)
    @JsonIgnore
    private Password password;

    @Convert(converter = FullName.FullNameConverter.class)
    @Column(nullable = false, length = 30)
    @JsonIgnore
    private FullName fullName;

    @Convert(converter = Phone.PhoneConverter.class)
    @Column(nullable = false, unique = true, length = 40)
    @JsonIgnore
    private Phone phone;

    @Embedded
    private Address address;

    @Embedded
    private Roles roles;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private MemberJpaEntity(Long id, Email email, Username username, Password password, FullName fullName, Phone phone,
                            Address address, Roles roles, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
    }

    public static MemberJpaEntity from(Member member, CryptoProvider cryptoProvider) {
        Email emailToEncrypt = member.getEmail();
        Username usernameToEncrypt = member.getUsername();
        FullName fullNameToEncrypt = member.getFullName();
        Phone phoneToEncrypt = member.getPhone();
        Address addressToEncrypt = member.getAddress();

        return MemberJpaEntity.builder()
                .email(emailToEncrypt.encrypt(cryptoProvider))
                .username(usernameToEncrypt.encrypt(cryptoProvider))
                .password(member.getPassword())
                .fullName(fullNameToEncrypt.encrypt(cryptoProvider))
                .phone(phoneToEncrypt.encrypt(cryptoProvider))
                .address(addressToEncrypt.encrypt(cryptoProvider))
                .roles(member.getRoles())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
