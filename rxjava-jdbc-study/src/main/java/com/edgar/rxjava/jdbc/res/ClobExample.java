package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.io.Reader;

public class ClobExample {

/*
* create table note(
    id bigint auto_increment primary key,
    text varchar(255)
)*/
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        //Insert a Clob
        String document = null;
        Observable<Integer> count = db
                .update("insert into person_clob(name,document) values(?,?)")
                .parameter("FRED")
                .parameter(Database.toSentinelIfNull(document)).count();

        //or
        Reader reader = null;
         count = db
                .update("insert into person_clob(name,document) values(?,?)")
                .parameter("FRED")
                .parameter(reader).count();

        //Insert a Null Clob
        count = db
                .update("insert into person_clob(name,document) values(?,?)")
                .parameter("FRED")
                .parameterClob(null).count();
        //or
        count = db
                .update("insert into person_clob(name,document) values(?,?)")
                .parameter("FRED")
                .parameter(Database.NULL_CLOB).count();

        //Read a Clob
        Observable<String> document2 = db.select("select document from person_clob")
                .getAs(String.class);

        //or
        Observable<Reader> document3 = db.select("select document from person_clob")
                .getAs(Reader.class);
    }
}