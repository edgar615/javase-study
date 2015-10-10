package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.tuple.Tuple2;

import java.util.List;

public class Tuple2Example {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        Tuple2<String, String> names = db.select("select alarm_user_id, alarm_user_name from alarm_user")
               .getAs(String.class, String.class).last().toBlocking().single();
        System.out.println(names);
    }
}