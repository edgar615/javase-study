package thread.slipped;

/*
线程在改变isLocked之前就允许其他线程来访问isLocked，引起slipped conditions
需要将isLocked = true;移动到检查isLocked的同步块中
 */
public class Lock {
    private boolean isLocked = true;

    public void lock(){
      synchronized(this){
        while(isLocked){
          try{
            this.wait();
          } catch(InterruptedException e){
            //do nothing, keep waiting
          }
        }
      }

      synchronized(this){
        isLocked = true;
      }
    }

    public synchronized void unlock(){
      isLocked = false;
      this.notify();
    }
}