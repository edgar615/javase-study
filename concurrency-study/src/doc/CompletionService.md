CompletionService将生产新的异步任务与使用已完成任务的结果分离开来的服务。生产者 submit 执行的任务。使用者 take 已完成的任务，并按照完成这些任务的顺序处理它们的结果。例如，CompletionService 可以用来管理异步 IO ，执行读操作的任务作为程序或系统的一部分提交，然后，当完成读操作时，会在程序的不同部分执行其他操作，执行操作的顺序可能与所请求的顺序不同。
 
 <pre>
 public class CompletionServiceExample {
  
     public static class Task implements Callable<Integer> {
         private int i;
  
         Task(int i) {
             this.i = i;
         }
  
         @Override
         public Integer call() throws Exception {
             Thread.sleep(new Random().nextInt(5000));
             System.out.println(Thread.currentThread().getName() + "   " + i);
             return i;
         }
     }
 
     public static void main(String[] args) {
         ExecutorService pool = Executors.newFixedThreadPool(10);
         CompletionService<Integer> completionServcie = new ExecutorCompletionService<Integer>(
                 pool);
         try {
             for (int i = 0; i < 10; i++) {
                 completionServcie.submit(new CompletionServiceExample.Task(i));
             }
             for (int i = 0; i < 10; i++) {
                 // take 方法等待下一个结果并返回 Future 对象。
                 // poll 不等待，有结果就返回一个 Future 对象，否则返回 null。
                 System.out.println(completionServcie.take().get());
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         } catch (ExecutionException e) {
             e.printStackTrace();
         } finally {
             pool.shutdown();
         }
     }
 }
 </pre>