package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;

import java.util.List;

public class AutoMapExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        List<AlarmUser> names = db.select("select alarm_user_id, alarm_user_name from alarm_user")
               .autoMap(AlarmUser.class).toList().toBlocking().single();
        System.out.println(names);

        List<AlarmUser2> alarmUser2 = db.select("select alarm_user_id, alarm_user_name from alarm_user")
                .autoMap(AlarmUser2.class).toList().toBlocking().single();
        System.out.println(alarmUser2);
    }
}