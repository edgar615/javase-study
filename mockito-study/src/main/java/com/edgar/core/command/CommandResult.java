package com.edgar.core.command;

/**
 * 命令处理类返回的结果对象
 *
 * @param <T> 返回结果
 * @author Edgar Zhang
 * @version 1.0
 */
public final class CommandResult<T> {
    private final T result;

    private CommandResult(T result) {
        super();
        this.result = result;
    }

    /**
     * 创建一个CommandResult对象
     *
     * @param result 返回结果
     * @param <T>    返回结果
     * @return CommandResult
     */
    public static <T> CommandResult<T> newInstance(T result) {
        return new CommandResult<T>(result);
    }

    public T getResult() {
        return result;
    }
}