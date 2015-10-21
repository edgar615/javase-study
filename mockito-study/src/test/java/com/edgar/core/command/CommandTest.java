package com.edgar.core.command;

import com.edgar.module.sys.repository.domain.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class CommandTest {

    @Autowired
    private CommandBus commandBus;

    @Test
    public void test() {
        Command command = new GetSysUser(1);
        CommandResult<SysUser> result = commandBus.executeCommand(command);
        Assert.assertNotNull(result.getResult());
    }

    @Test
    public void testChain() {
        Command command = new ChainGetSysUser(1);
        CommandResult<SysUser> result = commandBus.executeCommand(command);
        Assert.assertNotNull(result.getResult());
    }

    @Test
    public void testBatch() {
        List<Command> commands = new ArrayList<Command>();
        commands.add(new GetSysUser(1));
        commands.add(new ChainGetSysUser(1));
        List<CommandResult> result = commandBus.executeCommands(commands);
        Assert.assertNotNull(result.get(0).getResult());
    }

    @Test(expected = RuntimeException.class)
    public void testError() {
        Command command = new DeleteSysUser(1);
        commandBus.executeCommand(command);
    }

}
