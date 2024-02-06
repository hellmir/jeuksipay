package personal.jeuksipay.common.excpeption;

public abstract class AbstractSignificantException extends RuntimeException {
    public AbstractSignificantException(String message) {
        super(message);
    }

    abstract public int getStatusCode();
}
