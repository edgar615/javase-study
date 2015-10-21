package com.edgar.core.command;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 命令调度类的接口.
 *
 * @author Edgar
 * @version 1.0
 */
@Validated
public interface CommandBus {

    /**
     * 调度命令.
     *
     * @param command 命令
     * @param <T>     返回的结果类型
     * @return {@link CommandResult}
     */
    <T> CommandResult<T> executeCommand(@NotNull Command command);

    /**
     * 调度一组命令.
     *
     * @param commands 命令的集合
     * @return {@link CommandResult}
     */
    List<CommandResult> executeCommands(@NotEmpty List<Command> commands);

}
