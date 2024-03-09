package personal.jeuksipay.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.member.MemberTestConstant.*;

class AddressTest {
    @DisplayName("동일한 주소를 전송하면 동등한 Address 인스턴스를 생성한다.")
    @Test
    void fromSameValue() {
        // given, when
        Address address1 = Address.of(CITY1, STREET1, ZIPCODE1, DETAILED_ADDRESS1);
        Address address2 = Address.of(CITY1, STREET1, ZIPCODE1, DETAILED_ADDRESS1);

        // then
        assertThat(address1).isEqualTo(address2);
        assertThat(address1.hashCode()).isEqualTo(address2.hashCode());
    }

    @DisplayName("다른 주소를 전송하면 동등하지 않은 Address 인스턴스를 생성한다.")
    @Test
    void fromDifferentValue() {
        // given, when
        Address address1 = Address.of(CITY1, STREET1, ZIPCODE1, DETAILED_ADDRESS1);
        Address address2 = Address.of(CITY2, STREET2, ZIPCODE2, DETAILED_ADDRESS2);

        // then
        assertThat(address1).isNotEqualTo(address2);
        assertThat(address1.hashCode()).isNotEqualTo(address2.hashCode());
    }

    @DisplayName("주소의 일부를 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "null, 테헤란로, 12345, 서울특별시 강남구 테헤란로 2길",
            "서울, null, 12345, 서울특별시 강남구 테헤란로 2길",
            "서울, 테헤란로, null, 서울특별시 강남구 테헤란로 2길",
            "서울, 테헤란로, 12345, null",
    })
    void fromBlankValue(String city, String street, String zipcode, String detailedAddress) {
        // given, when, then
        assertThatThrownBy(() -> Address.of(city, street, zipcode, detailedAddress))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효하지 않은 주소를 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "서울특별시임, 테헤란로, 12345, 서울특별시 강남구 테헤란로 2길",
            "서울, 테헤란 street, 12345, 서울특별시 강남구 테헤란로 2길",
            "서울, 테헤란로, 123456, 서울특별시 강남구 테헤란로 2길",
            "서울, 테헤란로, 12345, 서울특별시 강남구 테헤란로 2길!",
    })
    void fromInvalidValue(String city, String street, String zipcode, String detailedAddress) {
        // given, when, then
        assertThatThrownBy(() -> Address.of(city, street, zipcode, detailedAddress))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
