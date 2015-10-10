package com.edgar.rxjava.jdbc.start;

import com.github.davidmoten.rx.jdbc.Database;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class MultiParameterExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        List<String> names = db.select("select alarm_user_name from alarm_user where  alarm_user_id = ?")
                .parameter("001205151324410000020758933716").parameter("001205151324410000120758933716").getAs(String.class).toList().toBlocking().single();
        System.out.println(names);
    }
}
