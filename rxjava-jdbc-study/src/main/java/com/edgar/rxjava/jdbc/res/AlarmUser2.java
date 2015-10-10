package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.annotations.Column;

/**
 * Created by Administrator on 2015/10/10.
 */
public interface AlarmUser2 {

    @Column
    String alarmUserName();

    @Column
    String alarmUserId();
}
