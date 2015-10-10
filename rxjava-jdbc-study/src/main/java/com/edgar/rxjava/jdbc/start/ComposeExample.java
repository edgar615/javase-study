package com.edgar.rxjava.jdbc.start;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class ComposeExample {

    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        // use composition to find the first person alphabetically with
// a score less than the person with the last name alphabetically
// whose name is not XAVIER. Two threads and connections will be used.
        Observable<String> ids = db.select("select alarm_user_id from alarm_user  order by alarm_user_id limit ?")
                .parameter(1).getAs(String.class).last();
        String names = db.select("select alarm_user_name from alarm_user where alarm_user_id = ?")
                .parameters(ids).getAs(String.class).first().toBlocking().single();
        System.out.println(names);

        // alternatively using the Observable.lift() method to chain everything in one command:
        names = db.select("select alarm_user_id from alarm_user  order by alarm_user_id limit ?")
                .parameter(1).getAs(String.class).last().lift(db.select("select alarm_user_name from alarm_user where alarm_user_id = ?")
                .parameterOperator().getAs(String.class)).first().toBlocking().single();
        System.out.println(names);
    }
}
