package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.io.InputStream;
import java.io.Reader;

public class BlobExample {

    /*
    * create table note(
        id bigint auto_increment primary key,
        text varchar(255)
    )*/
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        //Insert a Blob
        byte[] bytes = new byte[0];
        Observable<Integer> count = db
                .update("insert into person_blob(name,document) values(?,?)")
                .parameter("FRED")
                .parameter(Database.toSentinelIfNull(bytes)).count();


        //Insert a Null Blob
        count = db
                .update("insert into person_blob(name,document) values(?,?)")
                .parameter("FRED")
                .parameterBlob(null).count();
        //or
        count = db
                .update("insert into person_clob(name,document) values(?,?)")
                .parameter("FRED")
                .parameter(Database.NULL_BLOB).count();

        //Read a Blob
        Observable<byte[]> document = db.select("select document from person_clob")
                .getAs(byte[].class);

        //or
        Observable<InputStream> document2 = db.select("select document from person_clob")
                .getAs(InputStream.class);
    }
}