package personal.jeuksipay.common.excpeption;

public abstract class AbstractGeneralException extends RuntimeException {
    public AbstractGeneralException(String message) {
        super(message);
    }

    abstract public int getStatusCode();
}
