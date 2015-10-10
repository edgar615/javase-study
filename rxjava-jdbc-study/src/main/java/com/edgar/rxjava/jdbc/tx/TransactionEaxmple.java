package com.edgar.rxjava.jdbc.tx;

import com.github.davidmoten.rx.RxUtil;
import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

/**
 * Created by Administrator on 2015/10/10.
 */
public class TransactionEaxmple {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        long count = db
                .select("select count(*) from note")
                        // return as Long
                .getAs(Long.class)
                        // get answer
                .toBlocking().single();
        System.out.println(count);

        Observable<Boolean> begin = db.beginTransaction();
        Observable<Integer> updateCount = db
                // set everyones score to 99
                .update("insert into note(text) values(?)")
                        // is within transaction
                .dependsOn(begin)
                        // new score
                .parameter("hello")
                        // execute
                .count();
        Observable<Boolean> commit = db.commit(updateCount);
        count = db
                .select("select count(*) from note")
                        // depends on
                .dependsOn(commit)
                        // return as Long
                .getAs(Long.class)
                        // log
                .doOnEach(RxUtil.log())
                        // get answer
                .toBlocking().single();
        System.out.println(count);
    }
}
