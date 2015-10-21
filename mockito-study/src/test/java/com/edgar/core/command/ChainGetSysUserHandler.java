package com.edgar.core.command;

import com.edgar.module.sys.repository.domain.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public class ChainGetSysUserHandler implements CommandHandler<ChainGetSysUser> {

    @Override
    public CommandResult<SysUser> execute(ChainGetSysUser command) {
        System.out.println(command.getClass());
        return CommandResult.newInstance(new SysUser());
    }

}
