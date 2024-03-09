package personal.jeuksipay.member.domain.exception.general;

import org.springframework.http.HttpStatus;
import personal.jeuksipay.common.excpeption.AbstractGeneralException;

/**
 * 중복된 이메일 주소 또는 전화번호
 * 이메일 주소나 전화번호는 사용자마다 고유하므로, 중복 가입일 가능성이 높음
 */

public class DuplicateMemberException extends AbstractGeneralException {
    public DuplicateMemberException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
