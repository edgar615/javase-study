package concurrencyinpractice.chapter02;

import concurrencyinpractice.annotation.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {

	private ExpensiveObject instance = null;

	public ExpensiveObject getInstance() {
        if (instance == null) {
			instance = new ExpensiveObject();
		}
		return instance;
	}
}