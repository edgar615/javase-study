

<pre>
Stopwatch stopwatch = Stopwatch.createStarted();
TimeUnit.SECONDS.sleep(1);
Assert.assertTrue(stopwatch.isRunning());
stopwatch.stop();
System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));

stopwatch = Stopwatch.createUnstarted();
Assert.assertFalse(stopwatch.isRunning());
stopwatch.start();
TimeUnit.SECONDS.sleep(1);
stopwatch.stop();
System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
</pre>