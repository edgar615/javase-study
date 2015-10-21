package com.edgar.core.command;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令调度类的实现.
 *
 * @author Edgar
 * @version 1.0
 */
@Service
public class CommandBusImpl implements CommandBus, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CommandBusImpl.class);

    /**
     * Spring的上下文
     */
    private static ApplicationContext APPLICATION_CONTEXT;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> CommandResult<T> executeCommand(Command command) {
        CommandHandler commandHandler;
        commandHandler = getCommandHandler(command);
        LOGGER.debug("request command is {}", ToStringBuilder
                .reflectionToString(command, ToStringStyle.SHORT_PREFIX_STYLE));
        if (command instanceof ChainCommand) {
            ChainCommand chainCommand = (ChainCommand) command;
            CommandResult<T> result = commandHandler.execute(command);
            Command nextCommand = chainCommand.nextCommand();
            if (nextCommand == null || nextCommand instanceof UnResolvedCommand) {
                return result;
            }
            LOGGER.debug("command in chain，next command is {}", ToStringBuilder
                    .reflectionToString(nextCommand,
                            ToStringStyle.SHORT_PREFIX_STYLE));
            return executeCommand(nextCommand);
        }
        return commandHandler.execute(command);
    }

    @Override
    public List<CommandResult> executeCommands(List<Command> commands) {
        LOGGER.debug("batch execute {} commands", commands.size());
        List<CommandResult> results = new ArrayList<CommandResult>(commands.size());
        for (Command command : commands) {
            results.add(executeCommand(command));
        }
        return results;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        APPLICATION_CONTEXT = context;
    }

    /**
     * 根据命令对象获取处理类
     *
     * @param command 命令对象
     * @return 命令处理类
     */
    @SuppressWarnings("rawtypes")
    private CommandHandler getCommandHandler(Command command) {
        Validate.notNull(command, "command cannot be null");
        Validate.isTrue(!(command instanceof UnResolvedCommand),
                "UnResolvedCommand donot has hander");
        String handlerId = command.getClass().getSimpleName() + "Handler";
        handlerId = StringUtils.uncapitalize(handlerId);
        return APPLICATION_CONTEXT.getBean(handlerId,
                CommandHandler.class);
    }

}
