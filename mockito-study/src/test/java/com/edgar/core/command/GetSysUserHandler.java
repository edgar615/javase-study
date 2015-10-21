package com.edgar.core.command;

import com.edgar.module.sys.repository.domain.SysUser;
import org.springframework.stereotype.Service;

@Service
public class GetSysUserHandler implements CommandHandler<GetSysUser> {

    @Override
    public CommandResult<SysUser> execute(GetSysUser command) {
        System.out.println(command.getClass());
        return CommandResult.newInstance(new SysUser());
    }

}