package personal.jeuksipay.member.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private Long id;
    private Email email;
    private final Username username;
    private Password password;
    private OauthName oauthName;
    private final FullName fullName;
    private Phone phone;
    private Address address;
    private final Roles roles;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime lastLoggedInAt;

    @Builder
    private Member(Long id, Email email, Username username, Password password, OauthName oauthName,
                   FullName fullName, Phone phone, Address address, Roles roles,
                   LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime lastLoggedInAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.oauthName = oauthName;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.lastLoggedInAt = lastLoggedInAt;
    }

    public static Member of(String oauthEmail, String oauthName) {
        return Member.builder()
                .email(Email.of(oauthEmail))
                .oauthName(OauthName.of(oauthName))
                .build();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Member member = (Member) object;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email)
                && Objects.equals(username, member.username) && Objects.equals(password, member.password)
                && Objects.equals(oauthName, member.oauthName) && Objects.equals(fullName, member.fullName)
                && Objects.equals(phone, member.phone) && Objects.equals(address, member.address)
                && Objects.equals(roles, member.roles) && Objects.equals(createdAt, member.createdAt)
                && Objects.equals(modifiedAt, member.modifiedAt)
                && Objects.equals(lastLoggedInAt, member.lastLoggedInAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, oauthName,
                fullName, phone, address, roles, createdAt, modifiedAt, lastLoggedInAt);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updateEmail(String email) {
        this.email = Email.of(email);
        modifiedAt = LocalDateTime.now();
    }

    public void updatePhone(String phone) {
        this.phone = Phone.of(phone);
        modifiedAt = LocalDateTime.now();
    }

    public void updatePassword(String passwordToChange, PasswordEncoder passwordEncoder) {
        this.password = Password.from(passwordToChange, passwordEncoder);
        modifiedAt = LocalDateTime.now();
    }

    public void updateOauthName(String oauthName) {
        this.oauthName = OauthName.of(oauthName);
        modifiedAt = LocalDateTime.now();
    }
}
