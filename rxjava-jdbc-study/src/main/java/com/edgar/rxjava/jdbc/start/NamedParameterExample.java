package com.edgar.rxjava.jdbc.start;

import com.github.davidmoten.rx.jdbc.Database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/10.
 */
public class NamedParameterExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        List<String> names = db.select("select alarm_user_name from alarm_user where  alarm_user_id = :alarmUserId")
                .parameter("alarmUserId", "001205151324410000020758933716").getAs(String.class).toList().toBlocking().single();
        System.out.println(names);

        Map<String, String> map = new HashMap<String, String>();
        map.put("alarmUserId", "001205151324410000020758933716");
        names = db.select("select alarm_user_name from alarm_user where  alarm_user_id = :alarmUserId")
                .parameters(map).getAs(String.class).toList().toBlocking().single();
        System.out.println(names);

//        Observable<String> names = db
//                .select("select name from person where score >= :min and score <=:max")
//                .parameters(Observable.just(map1, map2))
//                .getAs(String.class);
    }
}
