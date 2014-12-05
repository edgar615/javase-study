package concurrencyinpractice.chapter04;

import concurrencyinpractice.annotation.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class PublishingVehicleTracker {

	private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

	public PublishingVehicleTracker(Map<String, SafePoint> locations,
			Map<String, SafePoint> unmodifiableMap) {
		this.locations = new ConcurrentHashMap<String, SafePoint>(locations);
		this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
	}

	public synchronized Map<String, SafePoint> getLocations() {
		return locations;
	}

	public synchronized Map<String, SafePoint> getUnmodifiableMap() {
		return unmodifiableMap;
	}

	public void setLocation(String id, int x, int y) {
		if (!locations.containsKey(id)) {
			throw new IllegalArgumentException("invalid vehicle name: " + id);
		}
		locations.get(id).set(x, y);
	}
}