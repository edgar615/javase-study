package com.edgar.rxjava.jdbc.start;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.Util;
import rx.Observable;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class DependExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        Observable<Integer> insert = db.update("insert into alarm_user(alarm_user_id, alarm_user_name) values(?,?)")
                .parameters("abcdefg", "hahaha").count();
        int count = db.select("select alarm_user_name from alarm_user")
                .dependsOn(insert).count().toBlocking().single();
        System.out.println(count);

    }
}
