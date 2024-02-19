package personal.jeuksipay.member.domain;

import lombok.Builder;
import lombok.Getter;
import personal.jeuksipay.member.domain.security.Password;
import personal.jeuksipay.member.domain.wrapper.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private Long id;
    private Email email;
    private final Username username;
    private final Password password;
    private final FullName fullName;
    private final Phone phone;
    private Address address;
    private final Roles roles;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime lastLoggedInAt;

    @Builder
    private Member(Long id, Email email, Username username, Password password,
                   FullName fullName, Phone phone, Address address, Roles roles,
                   LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime lastLoggedInAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.lastLoggedInAt = lastLoggedInAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email)
                && Objects.equals(username, member.username) && Objects.equals(password, member.password)
                && Objects.equals(fullName, member.fullName) && Objects.equals(phone, member.phone)
                && Objects.equals(address, member.address) && Objects.equals(roles, member.roles)
                && Objects.equals(createdAt, member.createdAt) && Objects.equals(modifiedAt, member.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, password, fullName, phone, address, roles, createdAt, modifiedAt);
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
}
