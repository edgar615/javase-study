package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;

import java.util.List;

public class GetExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        List<String> names = db.select("select alarm_user_id, alarm_user_name from alarm_user")
                .get(rs -> rs.getString(1) + "-" + rs.getString(2)).toList().toBlocking().single();
        System.out.println(names);

    }
}