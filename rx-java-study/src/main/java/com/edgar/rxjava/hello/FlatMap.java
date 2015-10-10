package com.edgar.rxjava.hello;

import rx.Observable;

/**
 * Created by Administrator on 2015/10/10.
 */
public class FlatMap {
    public static void main(String[] args) {
        //Observable.flatMap() takes the emissions of one Observable and returns the emissions of another Observable to take its place. It's the ol' switcheroo:
        // you thought you were getting one stream of items but instead you get another.
//        query("Hello, world!")
//                .flatMap(urls -> Observable.from(urls))
//                .flatMap(new Func1<String, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(String url) {
//                        return getTitle(url);
//                    }
//                })
//                .subscribe(title -> System.out.println(title));

        //or
//        query("Hello, world!")
//                .flatMap(urls -> Observable.from(urls))
//                .flatMap(url -> getTitle(url))
//                .subscribe(title -> System.out.println(title));

        //filter
//        query("Hello, world!")
//                .flatMap(urls -> Observable.from(urls))
//                .flatMap(url -> getTitle(url))
//                .filter(title -> title != null)
//                .subscribe(title -> System.out.println(title));

        //take
//        query("Hello, world!")
//                .flatMap(urls -> Observable.from(urls))
//                .flatMap(url -> getTitle(url))
//                .filter(title -> title != null)
//                .take(5)
//                .subscribe(title -> System.out.println(title));

        //doOnNext() allows us to add extra behavior each time an item is emitted, in this case saving the title.
//        query("Hello, world!")
//                .flatMap(urls -> Observable.from(urls))
//                .flatMap(url -> getTitle(url))
//                .filter(title -> title != null)
//                .take(5)
//                .doOnNext(title -> saveTitle(title))
//                .subscribe(title -> System.out.println(title));
    }
}
