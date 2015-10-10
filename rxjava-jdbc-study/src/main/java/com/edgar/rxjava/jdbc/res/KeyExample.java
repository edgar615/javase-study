package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.tuple.TupleN;
import rx.Observable;

public class KeyExample {

/*
* create table note(
    id bigint auto_increment primary key,
    text varchar(255)
)*/
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");
        Observable<Integer> keys =
                db.update("insert into note(text) values(?)")
                        .parameter("hello", "there")
                        .returnGeneratedKeys()
                        .getAs(Integer.class);
    }
}