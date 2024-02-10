package personal.jeuksipay.common.adapter.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static personal.jeuksipay.common.adapter.in.InputFormatValidator.ID_NO_VALUE_EXCEPTION;
import static personal.jeuksipay.common.adapter.in.InputFormatValidator.INVALID_ID_EXCEPTION;

class InputFormatValidatorTest {
    @DisplayName("유효한 ID를 전송하면 IllegalArgumentException이 발생하지 않는다.")
    @ParameterizedTest
    @CsvSource({"1", "2", "10", "100", "1234567890"})
    void validateIdByValidValue(String id) {
        // given, when, then
        InputFormatValidator.validateId(id);
    }

    @DisplayName("ID를 전송하지 않으면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" "})
    void validateIdByBlankValue(String id) {
        // given, when, then
        assertThatThrownBy(() -> InputFormatValidator.validateId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ID_NO_VALUE_EXCEPTION);
    }

    @DisplayName("유효하지 않은 ID를 전송하면 IllegalArgumentException이 발생한다.")
    @ParameterizedTest
    @CsvSource({"-1", "0", "abc", "ㄱㄴㄷ", "가나다"})
    void validateIdByInvalidValue(String id) {
        // given, when, then
        assertThatThrownBy(() -> InputFormatValidator.validateId(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_ID_EXCEPTION + id);
    }
}
