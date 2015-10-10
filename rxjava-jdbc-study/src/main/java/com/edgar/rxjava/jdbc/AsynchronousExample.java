package com.edgar.rxjava.jdbc;

import com.github.davidmoten.rx.RxUtil;
import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.github.davidmoten.rx.RxUtil.toEmpty;

/**
 * Created by Administrator on 2015/10/10.
 */
public class AsynchronousExample {
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        //After running this code you have no guarantee that the update person set score=1 ran before the update person set score=2.
        Database adb = db.asynchronous();
        Observable
                .just(1, 2, 3)
                .lift(adb.update("update person set score = ?")
                        .parameterOperator());

        // To run those queries synchronously either use a transaction:
        Observable
                .just(1, 2, 3)
                .lift(adb.update("update person set score = ?")
                        .dependsOn(db.beginTransaction())
                        .parameterOperator())
                .lift(adb.commitOnCompleteOperator());
    }
}
