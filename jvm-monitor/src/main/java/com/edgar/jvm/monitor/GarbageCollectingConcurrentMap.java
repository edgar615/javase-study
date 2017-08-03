package com.edgar.jvm.monitor;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 借助弱引用
 * <p>
 * How can I listen to the garbage collection on an object? This can be done using a java.lang
 * .ref.Reference (java.lang.ref.WeakReference for example) and a java.lang.ref.ReferenceQueue.
 * When the object inside the WeakReference is garbage collected, the WeakReference is put on a
 * ReferenceQueue. This makes it possible to do some cleanup by taking items from this
 * ReferenceQueue. This is the first big step to listen to garbage collection.
 *
 * @param <K>
 * @param <V>
 */
public class GarbageCollectingConcurrentMap<K, V> {

  private final static ReferenceQueue referenceQueue = new ReferenceQueue();

  private final ConcurrentMap<K, GarbageReference<K, V>> map =
          new ConcurrentHashMap<K, GarbageReference<K, V>>();

  public void clear() {
    map.clear();
  }

  public V get(K key) {
    GarbageReference<K, V> ref = map.get(key);
    return ref == null ? null : ref.value;
  }

  public Object getGarbageObject(K key) {
    GarbageReference<K, V> ref = map.get(key);
    return ref == null ? null : ref.get();
  }

  public Collection<K> keySet() {
    return map.keySet();
  }

  public void put(K key, V value, Object garbageObject) {
    if (key == null || value == null || garbageObject == null) throw new NullPointerException();
    if (key == garbageObject) {
      throw new IllegalArgumentException(
              "key can't be equal to garbageObject for gc to work");
    }
    if (value == garbageObject) {
      throw new IllegalArgumentException(
              "value can't be equal to garbageObject for gc to work");
    }

    GarbageReference reference = new GarbageReference(garbageObject, key, value, map);
    map.put(key, reference);
  }

  static class GarbageReference<K, V> extends WeakReference {
    final K key;

    final V value;

    final ConcurrentMap<K, V> map;

    GarbageReference(Object referent, K key, V value, ConcurrentMap<K, V> map) {
      super(referent, referenceQueue);
      this.key = key;
      this.value = value;
      this.map = map;
    }
  }

  static class CleanupThread extends Thread {
    CleanupThread() {
      setPriority(Thread.MAX_PRIORITY);
      setName("GarbageCollectingConcurrentMap-cleanupthread");
      setDaemon(true);
    }

    public void run() {
      while (true) {
        try {
          GarbageReference ref = (GarbageReference) referenceQueue.remove();
          while (true) {
            ref.map.remove(ref.key);
            ref = (GarbageReference) referenceQueue.remove();
          }
        } catch (InterruptedException e) {
          //ignore
        }
      }
    }
  }

  static {
    new CleanupThread().start();
  }
}