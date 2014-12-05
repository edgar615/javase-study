### 线程的属性

- ID: 每个线程的独特标识。
- Name: 线程的名称。
- Priority: 线程对象的优先级。优先级别在1-10之间，1是最低级，10是最高级。不建议改变它们的优先级，但是你想的话也是可以的。
- Status: 线程的状态。在Java中，线程只能有这6种中的一种状态： new, runnable, blocked, waiting, time waiting, 或 terminated.


<pre>
public class Calculator implements Runnable {

    private int number;
    public Calculator(int number) {
        this.number=number;
    }

    @Override
    public void run() {
        for (int i=1; i<=10; i++){
            System.out.printf("%s: %d * %d = %d\n",Thread.
                    currentThread().getName(),number,i,i*number);
        }
    }
}
</pre>

<pre>
public class Main {

    public static void main(String[] args) throws IOException {
        Thread[] threads = new Thread[10];
        Thread.State threadStates[] = new Thread.State[10];
        for (int i=0; i<10; i++){
            Calculator calculator=new Calculator(i);
            Thread thread=new Thread(calculator);
            threads[i] = thread;
            if ((i%2)==0){
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("Thread " + i);
        }
        try (FileWriter file = new FileWriter("log.txt");
             PrintWriter pw = new PrintWriter(file);){
            for (int i=0; i<10; i++) {
                pw.println("Main : Status of Thread " + i + " : " +
                        threads[i].getState());
                threadStates[i]=threads[i].getState();
            }
            for (int i=0; i<10; i++){
                threads[i].start();
            }

            boolean finish=false;
            while (!finish) {
                for (int i=0; i<10; i++){
                    if (threads[i].getState()!=threadStates[i]) {
                        writeThreadInfo(pw, threads[i],threadStates[i]);
                        threadStates[i]=threads[i].getState();
                    }
                }
                finish=true;
                for (int i=0; i<10; i++){
                    finish=finish &&(threads[i].getState()==Thread.State.TERMINATED);
                }
            }
        }
    }

    private static void writeThreadInfo(PrintWriter pw, Thread
            thread, Thread.State state) {
        pw.printf("Main : Id %d - %s\n",thread.getId(),thread.getName());
        pw.printf("Main : Priority: %d\n",thread.getPriority());
        pw.printf("Main : Old State: %s\n",state);
        pw.printf("Main : New State: %s\n",thread.getState());
        pw.printf("Main : ************************************\n");
    }
}
</pre>

### 中断线程

<pre>
public class PrimeGenerator implements Runnable {
    @Override
    public void run() {
        long number = 1l;
        while (true) {
            if (isPrime(number)) {
                System.out.printf("Number %d is Prime", number);
            }
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("The Prime Generator has been Interrupted");
                return;
            }
            number++;
        }
    }

    private boolean isPrime(long number) {
        if (number <= 2) {
            return true;
        }
        for (long i = 2; i < number; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
</pre>

<pre>
PrimeGenerator primeGenerator = new PrimeGenerator();
Thread t = new Thread(primeGenerator);
t.start();
TimeUnit.SECONDS.sleep(10);
t.interrupt();
</pre>

### 操作线程的中断
<pre>
public class FileSearch implements Runnable {

    private String initPath;
    private String fileName;

    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        File file = new File(initPath);
        if (file.isDirectory()) {
            try {
                directoryProcess(file);
            } catch (InterruptedException e) {
                System.out.printf("%s: The search has been interrupted",Thread.currentThread().getName());
            }
        }
    }

    private void directoryProcess(File file) throws
            InterruptedException {
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private void fileProcess(File file) throws InterruptedException
    {
        if (file.getName().equals(fileName)) {
            System.out.printf("%s : %s\n",Thread.currentThread().
                    getName() ,file.getAbsolutePath());
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }
}
</pre>

### sleep()和yield()
- yield() 暂停当前正在执行的线程对象，并执行其他线程。

### 等待线程完成

<pre>
public class FileClock implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.printf("%s\n", new Date());
			try {
				// Sleep during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.out.printf("The FileClock has been interrupted");
			}
		}
	}
}
</pre>

### 等待线程的终结
- join() 暂停当前线程终止，等待该线程终止
- join(long millis) 等待该线程终止的时间最长为 millis 毫秒。
- join(long millis, int nanos) 等待该线程终止的时间最长为 millis 毫秒 + nanos 纳秒。

<pre>
public class DataSourcesLoader implements Runnable {


	/**
     * Main method of the class
	 */
	@Override
	public void run() {
		
		// Writes a messsage
		System.out.printf("Begining data sources loading: %s\n",new Date());
		// Sleeps four seconds
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Writes a message
		System.out.printf("Data sources loading has finished: %s\n",new Date());
	}
}
</pre>

<pre>
public class NetworkConnectionsLoader implements Runnable {


	/**
     * Main method of the class
	 */
	@Override
	public void run() {
		// Writes a message
		System.out.printf("Begining network connections loading: %s\n",new Date());
		// Sleep six seconds
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Writes a message
		System.out.printf("Network connections loading has finished: %s\n",new Date());
	}
}
</pre>

<pre>
public static void main(String[] args) {

    // Creates and starts a DataSourceLoader runnable object
    DataSourcesLoader dsLoader = new DataSourcesLoader();
    Thread thread1 = new Thread(dsLoader,"DataSourceThread");
    thread1.start();

    // Creates and starts a NetworkConnectionsLoader runnable object
    NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
    Thread thread2 = new Thread(ncLoader,"NetworkConnectionLoader");
    thread2.start();

    // Wait for the finalization of the two threads
    try {
        thread1.join();
        thread2.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    // Waits a message
    System.out.printf("Main: Configuration has been loaded: %s\n",new Date());
}
</pre>

### 守护进程

Java有一种特别的线程叫做守护线程。这种线程的优先级非常低，通常在程序里没有其他线程运行时才会执行它。当守护线程是程序里唯一在运行的线程时，JVM会结束守护线程并终止程序。

根据这些特点，守护线程通常用于在同一程序里给普通线程（也叫使用者线程）提供服务。它们通常无限循环的等待服务请求或执行线程任务。它们不能做重要的任务，因为我们不知道什么时候会被分配到CPU时间片，并且只要没有其他线程在运行，它们可能随时被终止。JAVA中最典型的这种类型代表就是垃圾回收器。

- setDaemon(boolean on)  将该线程标记为守护线程或用户线程。

<pre>
public class WriterTask implements Runnable {

	Deque<Event> deque;
	
	public WriterTask (Deque<Event> deque){
		this.deque=deque;
	}
	
	@Override
	public void run() {
		
		// Writes 100 events
		for (int i=1; i<100; i++) {
			// Creates and initializes the Event objects 
			Event event=new Event();
			event.setDate(new Date());
			event.setEvent(String.format("The thread %s has generated an event",Thread.currentThread().getId()));
			
			// Add to the data structure
			deque.addFirst(event);
			try {
				// Sleeps during one second
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
</pre>

<pre>
public class CleanerTask implements Runnable {
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
    }

    @Override
    public void run() {
        while (true) {
            Date date = new Date();
            clean(date);
        }
    }

    private void clean(Date date) {
        long difference;
        boolean delete;

        if (deque.size()==0) {
            return;
        }

        delete=false;
        do {
            Event e = deque.getLast();
            difference = date.getTime() - e.getDate().getTime();
            if (difference > 10000) {
                System.out.printf("Cleaner: %s\n",e.getEvent());
                deque.removeLast();
                delete=true;
            }
        } while (difference > 10000);
        if (delete){
            System.out.printf("Cleaner: Size of the queue: %d\n",deque.size());
        }
    }
}
</pre>

<pre>
public static void main(String[] args) {

    Deque<Event> deque=new ArrayDeque<Event>();

    // Creates the three WriterTask and starts them
    WriterTask writer=new WriterTask(deque);
    for (int i=0; i<3; i++){
        Thread thread=new Thread(writer);
        thread.start();
    }

    // Creates a cleaner task and starts them
    CleanerTask cleaner=new CleanerTask(deque);
    Thread cleanerThread = new Thread(cleaner);
    cleanerThread.setDaemon(true);
    cleanerThread.start();
}
</pre>

### 处理不受控制的异常

-  	setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler eh) 设置该线程由于未捕获到异常而突然终止时调用的处理程序。

<pre>
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {


	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.printf("An exception has been captured\n");
		System.out.printf("Thread: %s\n",t.getId());
		System.out.printf("Exception: %s: %s\n",e.getClass().getName(),e.getMessage());
		System.out.printf("Stack Trace: \n");
		e.printStackTrace(System.out);
		System.out.printf("Thread status: %s\n",t.getState());
	}

}
</pre>

<pre>
public static void main(String[] args) {
    Task task=new Task();
    Thread thread=new Thread(task);
    thread.setUncaughtExceptionHandler(new ExceptionHandler());
    thread.start();
    try {
        thread.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.printf("Thread has finished\n");

}
</pre>