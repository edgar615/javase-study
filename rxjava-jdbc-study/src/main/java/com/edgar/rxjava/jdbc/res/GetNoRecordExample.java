package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GetNoRecordExample {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        db.select("select alarm_user_id, alarm_user_name from alarm_user where alarm_user_id = ?")
                .parameter("001205151325580000420758933716")
                .get(rs -> {
                    System.out.println(Thread.currentThread().getId());
                    return rs.getString(1) + "-" + rs.getString(2);
                })
                .singleOrDefault("no record")
                .subscribe(rs -> {
                    latch.countDown();
                    System.out.println(Thread.currentThread().getId());
                    System.out.println("subscribe");
                    System.out.println(rs);
                });
//        .get(rs -> rs.getString(1) + "-" + rs.getString(2))

        latch.await();

    }
}