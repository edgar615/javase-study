package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.tuple.Tuple2;
import com.github.davidmoten.rx.jdbc.tuple.TupleN;

public class TupleNExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        TupleN<String> names = db.select("select alarm_user_id, alarm_user_name, org_code from alarm_user")
               .getTupleN(String.class).last().toBlocking().single();
        System.out.println(names);
    }
}