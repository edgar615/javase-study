package exception;

/**
 * 异常码.
 *
 * @author Edgar
 */
public enum AppErrorCode implements ErrorCode {

    /**
     * 空指针异常.
     */
    NULL(0);

    /**
     * 异常编码
     */
    private final int number;

    /**
     * @param number 异常码
     */
    private AppErrorCode(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

}