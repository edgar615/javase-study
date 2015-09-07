package com.edgar.vertx.file;

import com.edgar.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;

/**
 * Created by Administrator on 2015/9/2.
 */
public class FileSystemExample extends AbstractVerticle {

    public static void main(String[] args) {
        Runner.runExample(FileSystemExample.class);
    }

    @Override
    public void start() throws Exception {
        FileSystem fs = vertx.fileSystem();

        // Check existence and delete
        fs.exists("bar.txt", result -> {
           if (result.succeeded() && result.result()) {
               fs.delete("bar.txt", r -> {
                  System.out.println("File deleted");
               });
           } else {
               System.err.println("Oh oh ... - cannot delete the file: " + result.cause());
           }
        });

        // Copy file from foo.txt to bar.txt synchronously
//        fs.copyBlocking("foo.txt", "bar.txt");
        fs.copy("foo.txt", "bar.txt", res -> {
            if (res.succeeded()) {
                // ok
                System.out.println("copied ok!");
            } else {
                //error
                System.err.println("Something went wrong!");
                System.err.println(res.cause());
            }
        });

        //read a file
       fs.readFile("foo.txt", result -> {
          if (result.succeeded()) {
              System.out.println(result.result());
          } else {
              System.err.println("Oh oh ..." + result.cause());
          }
       });

        //copy a file
        fs.copy("foo.txt", "bar.txt", result -> {
            if (result.succeeded()) {
                System.out.println("File copied");
            } else {
                System.err.println("Oh oh ..." + result.cause());
            }
        });

        //write a file
        fs.writeFile("hello.txt", Buffer.buffer("Hello"), result -> {
            if (result.succeeded()) {
                System.out.println("File written");
            } else {
                System.err.println("Oh oh ..." + result.cause());
            }
        });
    }
}
