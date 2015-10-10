package com.edgar.rxjava.jdbc.tx;

import com.github.davidmoten.rx.RxUtil;
import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.util.List;

import static com.github.davidmoten.rx.RxUtil.toEmpty;

/**
 * Created by Administrator on 2015/10/10.
 */
public class OnNextTransactionEaxmple {

    /**
     * create table person(
     id bigint auto_increment primary key,
     score int
     )
     * @param args
     */
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        List<Integer> mins = Observable
                // do 3 times
                .just(11, 12, 13)
                        // begin transaction for each item
                .lift(db.beginTransactionOnNextOperator())
                        // update all scores to the item
                .lift(db.update("update person set score=?").parameterOperator())
                        // to empty parameter list
                .map(toEmpty())
                        // increase score
                .lift(db.update("update person set score=score + 5").parameterListOperator())
                        //only expect one result so can flatten
                .lift(RxUtil.<Integer>flatten())
                        // commit transaction
                .lift(db.commitOnNextOperator())
                        // to empty lists
                .map(toEmpty())
                        // return count
                .lift(db.select("select min(score) from person").parameterListOperator()
                        .getAs(Integer.class))
                        // list the results
                .toList()
                        // block and get
                .toBlocking().single();

        System.out.println(mins);
    }
}
