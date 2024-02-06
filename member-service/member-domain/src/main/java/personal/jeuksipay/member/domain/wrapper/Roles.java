package personal.jeuksipay.member.domain.wrapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.ROLE_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_ROLE_EXCEPTION;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Roles {
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<Role> roles;

    private Roles(List<Role> roles) {
        this.roles = roles;
    }

    public static Roles from(List<String> roles) {
        validate(roles);

        List<Role> parsedRoles = new ArrayList<>();
        for (String role : roles) {
            parsedRoles.add(parseRole(role));
        }

        return new Roles(parsedRoles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles1 = (Roles) o;
        return Objects.equals(roles, roles1.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roles);
    }

    public Role get(int index) {
        return roles.get(index);
    }

    public boolean containBusinessUser() {
        for (Role role : roles) {
            if (role.isBusinessUser()) {
                return true;
            }
        }

        return false;
    }

    public boolean containGeneralUser() {
        for (Role role : roles) {
            if (role.isGeneralUser()) {
                return true;
            }
        }

        return false;
    }

    private List<String> toStrings() {
        return roles.stream().map(Role::toString).collect(Collectors.toList());
    }

    private static void validate(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException(ROLE_NO_VALUE_EXCEPTION);
        }
    }

    private static Role parseRole(String role) {
        try {
            return Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_ROLE_EXCEPTION + role);
        }
    }
}
