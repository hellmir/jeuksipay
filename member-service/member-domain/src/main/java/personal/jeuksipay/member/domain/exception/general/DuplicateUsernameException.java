package personal.jeuksipay.member.domain.exception.general;

import org.springframework.http.HttpStatus;
import personal.jeuksipay.common.excpeption.AbstractGeneralException;

/**
 * 중복된 사용자 이름
 * 사용자 간 중복될 수 있으므로, 중복 가입으로 볼 수 없음
 */
public class DuplicateUsernameException extends AbstractGeneralException {
    public DuplicateUsernameException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
