package com.edgar.core.helper;

import com.edgar.module.sys.repository.domain.SysDict;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * 字典事件的监听类
 *
 * @author Edgar Zhang
 * @version 1.0
 */
@Service
public class SysDictEventListener implements ApplicationListener<SysDictEvent> {

    @Override
    public void onApplicationEvent(SysDictEvent event) {
        SysDict sysDict = (SysDict) event.getSource();
        System.out.println(sysDict);
    }

}