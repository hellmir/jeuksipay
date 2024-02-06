package personal.jeuksipay.member.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.MemberTestConstant.PHONE1;
import static personal.jeuksipay.member.MemberTestConstant.PHONE2;
import static personal.jeuksipay.member.domain.exception.message.BlankExceptionMessage.PHONE_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.member.domain.exception.message.InvalidExceptionMessage.INVALID_PHONE_EXCEPTION;

class PhoneTest {
    @DisplayName("동일한 전화번호를 전송하면 동등한 Phone 인스턴스를 생성한다.")
    @Test
    void ofSameValue() {
        // given, when
        Phone phone1 = Phone.of(PHONE1);
        Phone phone2 = Phone.of(PHONE1);

        // then
        assertThat(phone1).isEqualTo(phone2);
        assertThat(phone1.hashCode()).isEqualTo(phone2.hashCode());
    }

    @DisplayName("다른 전화번호를 전송하면 동등하지 않은 Phone 인스턴스를 생성한다.")
    @Test
    void ofDifferentValue() {
        // given, when
        Phone phone1 = Phone.of(PHONE1);
        Phone phone2 = Phone.of(PHONE2);

        // then
        assertThat(phone1).isNotEqualTo(phone2);
        assertThat(phone1.hashCode()).isNotEqualTo(phone2.hashCode());
    }

    @DisplayName("전화번호를 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void ofBlankValue(String phone) {
        // given, when, then
        assertThatThrownBy(() -> Phone.of(phone))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(PHONE_NO_VALUE_EXCEPTION);
    }

    @DisplayName("유효하지 않은 전화번호를 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"01112345678", "010123456789", "010123$5678", "010a2345678"})
    void ofInvalidValue(String phone) {
        // given, when, then
        assertThatThrownBy(() -> Phone.of(phone))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_PHONE_EXCEPTION);
    }
}
