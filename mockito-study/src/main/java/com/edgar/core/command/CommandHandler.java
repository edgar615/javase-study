package com.edgar.core.command;

/**
 * 命令的处理类.
 *
 * @param <T> 命令对象
 * @author Edgar
 * @version 1.0
 */
public interface CommandHandler<T extends Command> {

    /**
     * 命令的处理方法.
     *
     * @param command 命令
     * @return {@link com.edgar.core.command.CommandResult}
     */
    CommandResult<?> execute(T command);
}