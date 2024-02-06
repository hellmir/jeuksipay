package personal.jeuksipay.member.application.exception.significant;

import org.springframework.http.HttpStatus;
import personal.jeuksipay.common.excpeption.AbstractSignificantException;
import personal.jeuksipay.member.application.exception.message.IncorrectPasswordExceptionMessage;

public class IncorrectPasswordException extends AbstractSignificantException {
    public IncorrectPasswordException() {
        super(IncorrectPasswordExceptionMessage.INCORRECT_PASSWORD_EXCEPTION);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}