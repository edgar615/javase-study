package com.edgar.rxjava.jdbc.res;

import com.github.davidmoten.rx.RxUtil;
import com.github.davidmoten.rx.jdbc.Database;
import rx.Observable;

import java.io.InputStream;

import static com.github.davidmoten.rx.RxUtil.toEmpty;

public class LiftExample {

    /*
    * Using the Observable.lift() method you can perform multiple queries without breaking method chaining. Observable.lift() requires an Operator parameter which are available via

    db.select(sql).parameterOperator().getXXX()
    db.select(sql).parameterListOperator().getXXX()
    db.select(sql).dependsOnOperator().getXXX()
    db.update(sql).parameterOperator()
    db.update(sql).parameterListOperator()
    db.update(sql).dependsOnOperator()
*/
    public static void main(String[] args) {
        Database db = Database.from("jdbc:mysql://127.0.0.1:3306/alarm_user", "root", "");

        Observable<Integer> score = Observable
                // parameters for coming update
                .just(4, "FRED")
                        // update Fred's score to 4
                .lift(db.update("update person set score=? where name=?")
                        //parameters are pushed
                        .parameterOperator())
                        // update everyone with score of 4 to 14
                .lift(db.update("update person set score=? where score=?")
                        .parameters(14, 4)
                                //wait for completion of previous observable
                        .dependsOnOperator())
                        // get Fred's score
                .lift(db.select("select score from person where name=?")
                        .parameters("FRED")
                                //wait for completion of previous observable
                        .dependsOnOperator()
                        .getAs(Integer.class));

        //If the query does not require parameters you can push it an empty list and use the parameterListOperator() to force execution.
//        Observable<Integer> rowsAffected = Observable
//                //generate two integers
//                .range(1,2)
//                        //replace the integers with empty observables
//                .map(toEmpty())
//                        //execute the update twice with an empty list
//                .lift(db.update("update person set score = score + 1")
//                        .parameterListOperator())
//                        // flatten
//                .lift(RxUtil.<Integer> flatten())
//                        // total the affected records
//                .lift(SUM_INTEGER);
    }
}