

- execute(Runnable command) 在未来某个时间执行给定的命令。

<pre>
ExecutorService executorService = Executors.newFixedThreadPool(10);
executorService.execute(new Runnable() {
    @Override
    public void run() {
        System.out.println("Asynchronous task");
    }
});
executorService.shutdown();
</pre>

- submit(Runnable task) 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future。

<pre>
ExecutorService executorService = Executors.newFixedThreadPool(10);
Future future = executorService.submit(new Runnable() {
    @Override
    public void run() {
        System.out.println("Asynchronous task");
    }
});
System.out.println(future.get());
executorService.shutdown();
</pre>

-  submit(Callable<T> task) 提交一个返回值的任务用于执行，返回一个表示任务的未决结果的 Future。

<pre>
ExecutorService executorService = Executors.newFixedThreadPool(10);
Future<String> future = executorService.submit(new Callable<String>() {
    @Override
    public String call() throws Exception {
        System.out.println("Asynchronous task");
        return "Callable Result";
    }
});
System.out.println(future.get());
executorService.shutdown();
</pre>

- invokeAny(Collection<? extends Callable<T>> tasks)  执行给定的任务，如果某个任务已成功完成（也就是未抛出异常），则返回其结果。此方法一旦正常或异常返回后，则取消尚未完成的任务
- invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) 执行给定的任务，如果在给定的超时期满前某个任务已成功完成（也就是未抛出异常），则返回其结果

<pre>
ExecutorService executorService = Executors.newSingleThreadExecutor();

Set<Callable<String>> callables = new HashSet<Callable<String>>();

callables.add(new Callable<String>() {
    public String call() throws Exception {
        return "Task 1";
    }
});
callables.add(new Callable<String>() {
    public String call() throws Exception {
        return "Task 2";
    }
});
callables.add(new Callable<String>() {
    public String call() throws Exception {
        return "Task 3";
    }
});

String result = executorService.invokeAny(callables);

System.out.println("result = " + result);

executorService.shutdown();
</pre>

- invokeAll(Collection<? extends Callable<T>> tasks)  执行给定的任务，当所有任务完成时，返回保持任务状态和结果的 Future 列表。
- invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) 执行给定的任务，当所有任务完成或超时期满时（无论哪个首先发生），返回保持任务状态和结果的 Future 列表。

<pre>
ExecutorService executorService = Executors.newSingleThreadExecutor();

Set<Callable<String>> callables = new HashSet<Callable<String>>();

callables.add(new Callable<String>() {
    public String call() throws Exception {
        return "Task 1";
    }
});
callables.add(new Callable<String>() {
    public String call() throws Exception {
        return "Task 2";
    }
});
callables.add(new Callable<String>() {
    public String call() throws Exception {
        return "Task 3";
    }
});

List<Future<String>> futures = executorService.invokeAll(callables);

for (Future<String> future : futures) {
    System.out.println("result = " + future.get());
}

executorService.shutdown();
</pre>