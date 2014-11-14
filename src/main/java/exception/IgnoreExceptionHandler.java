package exception;

/**
 * Created by Administrator on 2014/11/14.
 */
public class IgnoreExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(String context, int code, String message, Throwable t) {

    }

    @Override
    public void raise(String context, int code, String message) {

    }
}
