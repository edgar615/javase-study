package concurrencyinpractice.chapter05;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Memoizer3<A, V> implements Computable<A, V> {

	private final Map<A, FutureTask<V>> cache = new ConcurrentHashMap<A, FutureTask<V>>();

	private final Computable<A, V> c;

	public Memoizer3(Computable<A, V> c) {
        this.c = c;
	}

	@Override
	public V compute(final A arg) throws InterruptedException {
		FutureTask<V> f = cache.get(arg);
		if (f == null) {
			Callable<V> eval = new Callable<V>() {

				@Override
				public V call() throws Exception {
					return c.compute(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			f = ft;
			cache.put(arg, ft);
			ft.run();// 调用c.compute发生在这里
		}
		try {
			return f.get();
		} catch (ExecutionException e) {
			throw LaunderThrowable.launderThrowable(e.getCause());
		}
	}

}