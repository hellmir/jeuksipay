package personal.jeuksipay.member.adapter.out.persistence.refreshtoken;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.jeuksipay.member.domain.RefreshToken;

import javax.persistence.*;
import java.util.List;

@Entity(name = "refresh_token")
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshTokenJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "refresh_token_roles", joinColumns = @JoinColumn(name = "refresh_token_id"))
    @Column(name = "role")
    List<String> roles;

    @Column(nullable = false)
    private String tokenValue;

    private RefreshTokenJpaEntity(String memberId, List<String> roles, String tokenValue) {
        this.memberId = memberId;
        this.roles = roles;
        this.tokenValue = tokenValue;
    }

    public static RefreshTokenJpaEntity from(RefreshToken refreshToken) {
        return new RefreshTokenJpaEntity(
                refreshToken.getMemberId(), refreshToken.getRoles(), refreshToken.getTokenValue()
        );
    }
}
