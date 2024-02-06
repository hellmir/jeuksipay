package personal.jeuksipay.member.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.ROLE_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_ROLE_EXCEPTION;
import static personal.jeuksipay.member.domain.wrapper.Role.*;

class RolesTest {
    @DisplayName("유효한 값을 전송하면 여러 회원 분류를 가진 일급 컬렉션을 생성할 수 있다.")
    @Test
    void from() {
        // given
        String admin = ROLE_ADMIN.toString();
        String businessUser = ROLE_BUSINESS_USER.toString();
        String generalUser = ROLE_GENERAL_USER.toString();

        // when
        Roles roles1 = Roles.from(List.of(generalUser));
        Roles roles2 = Roles.from(List.of(admin, businessUser));
        Roles roles3 = Roles.from(List.of(admin, businessUser, generalUser));

        // then
        assertThat(roles1.get(0)).isEqualTo(ROLE_GENERAL_USER);

        assertThat(roles2.get(0)).isEqualTo(ROLE_ADMIN);
        assertThat(roles2.get(1)).isEqualTo(ROLE_BUSINESS_USER);

        assertThat(roles3.get(0)).isEqualTo(ROLE_ADMIN);
        assertThat(roles3.get(1)).isEqualTo(ROLE_BUSINESS_USER);
        assertThat(roles3.get(2)).isEqualTo(ROLE_GENERAL_USER);
    }

    @DisplayName("동일한 회원 분류를 전송하면 동등한 Roles 인스턴스를 생성한다.")
    @Test
    void fromSameValue() {
        // given, when
        Roles roles1 = Roles.from(List.of(ROLE_ADMIN.toString(), ROLE_GENERAL_USER.toString()));
        Roles roles2 = Roles.from(List.of(ROLE_ADMIN.toString(), ROLE_GENERAL_USER.toString()));

        // then
        assertThat(roles1).isEqualTo(roles2);
        assertThat(roles1.hashCode()).isEqualTo(roles2.hashCode());
    }

    @DisplayName("다른 회원 분류를 전송하면 동등하지 않은 Roles 인스턴스를 생성한다.")
    @Test
    void fromDifferentValue() {
        // given, when
        Roles roles1 = Roles.from(List.of(ROLE_ADMIN.toString(), ROLE_GENERAL_USER.toString()));
        Roles roles2 = Roles.from(List.of(ROLE_ADMIN.toString(), ROLE_BUSINESS_USER.toString()));

        // then
        assertThat(roles1).isNotEqualTo(roles2);
        assertThat(roles1.hashCode()).isNotEqualTo(roles2.hashCode());
    }

    @DisplayName("회원 분류를 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @Test
    void fromBlankValue() {
        // given, when, then
        assertThatThrownBy(() -> Roles.from(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ROLE_NO_VALUE_EXCEPTION);

        assertThatThrownBy(() -> Roles.from(new ArrayList<String>()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ROLE_NO_VALUE_EXCEPTION);
    }

    @DisplayName("유효하지 않은 회원 분류를 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"ROLE_ADMI", "ROLEgeneralUser", "businessUser", "관리자", "판매자", "구매자"})
    void fromInvalidValue(String role) {
        // given, when, then
        assertThatThrownBy(() -> Roles.from(List.of(role)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_ROLE_EXCEPTION + role);
    }

    @DisplayName("사용자가 기업 고객의 권한을 갖고 있는지 여부를 확인한다.")
    @Test
    void checkContainbusinessUser() {
        // given
        String admin = ROLE_ADMIN.toString();
        String businessUser = ROLE_BUSINESS_USER.toString();
        String generalUser = ROLE_GENERAL_USER.toString();

        Roles roles1 = Roles.from(List.of(businessUser, generalUser));
        Roles roles2 = Roles.from(List.of(admin, generalUser));

        // when, then
        assertThat(roles1.containBusinessUser()).isTrue();
        assertThat(roles2.containBusinessUser()).isFalse();
    }

    @DisplayName("사용자가 일반 고객의 권한을 갖고 있는지 여부를 확인한다.")
    @Test
    void checkContaingeneralUser() {
        // given
        String admin = ROLE_ADMIN.toString();
        String businessUser = ROLE_BUSINESS_USER.toString();
        String generalUser = ROLE_GENERAL_USER.toString();

        Roles roles1 = Roles.from(List.of(businessUser, generalUser));
        Roles roles2 = Roles.from(List.of(admin, businessUser));

        // when, then
        assertThat(roles1.containGeneralUser()).isTrue();
        assertThat(roles2.containGeneralUser()).isFalse();
    }
}
