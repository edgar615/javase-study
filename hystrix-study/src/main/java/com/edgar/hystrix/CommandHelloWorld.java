package com.edgar.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;

import java.util.concurrent.ExecutionException;

public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here
        System.out.println(Thread.currentThread().getId());
        return "Hello " + name + "!";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        String s = new CommandHelloWorld("Bob").execute();

        //or
//        Future<String> s = new CommandHelloWorld("Bob").queue();
//        System.out.println(s.get());

        //or
        Observable<String> s = new CommandHelloWorld("Bob").observe();
        System.out.println(s.toBlocking().single());
        System.out.println(Thread.currentThread().getId());
    }
}