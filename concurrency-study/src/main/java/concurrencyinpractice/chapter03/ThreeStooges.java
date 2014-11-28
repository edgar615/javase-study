package concurrencyinpractice.chapter03;

import concurrencyinpractice.annotation.Immutable;

import java.util.HashSet;
import java.util.Set;

@Immutable
public final class ThreeStooges {

	private final Set<String> stooges = new HashSet<String>();

	public ThreeStooges() {
        stooges.add("Moe");
		stooges.add("Larry");
		stooges.add("Curly");
	}

	public boolean isStooge(String name) {
		return stooges.contains(name);
	}

}