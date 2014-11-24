package thread.lock;

public class Counter{
    private int count = 0;

	public int inc(){
		synchronized(this){
			return ++count;
		}
	}
}