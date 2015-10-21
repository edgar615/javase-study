package com.edgar.core.concurrency;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Striped locks holder, contains array of {@link java.util.concurrent.locks.ReentrantLock}, on which lock/unlock
 * operations are performed. Purpose of this is to decrease lock contention.
 * <p>When client requests lock, it gives an integer argument, from which target lock is derived as follows:
 * index of lock in array equals to <code>id & (locks.length - 1)</code>.
 * Since <code>locks.length</code> is the power of 2, <code>locks.length - 1</code> is string of '1' bits,
 * and this means that all lower bits of argument are taken into account.
 * <p>Number of locks it can hold is bounded: it can be from set {2, 4, 8, 16, 32, 64}.
 */
public class StripedLock {
    private final ReentrantLock[] locks;

    /**
     * Default ctor, creates 16 locks
     */
    public StripedLock() {
        this(4);
    }

    /**
     * Creates array of locks, size of array may be any from set {2, 4, 8, 16, 32, 64}
     *
     * @param storagePower size of array will be equal to <code>Math.pow(2, storagePower)</code>
     */
    public StripedLock(int storagePower) {
        if (!(storagePower >= 1 && storagePower <= 6)) {
            throw new IllegalArgumentException("storage power must be in [1..6]");
        }

        int lockSize = (int) Math.pow(2, storagePower);
        locks = new ReentrantLock[lockSize];
        for (int i = 0; i < locks.length; i++)
            locks[i] = new ReentrantLock();
    }

    /**
     * Locks lock associated with given id.
     *
     * @param id value, from which lock is derived
     */
    public void lock(int id) {
        getLock(id).lock();
    }

    /**
     * Unlocks lock associated with given id.
     *
     * @param id value, from which lock is derived
     */
    public void unlock(int id) {
        getLock(id).unlock();
    }

    /**
     * Map function between integer and lock from locks array
     *
     * @param id argument
     * @return lock which is result of function
     */
    private ReentrantLock getLock(int id) {
        return locks[id & (locks.length - 1)];
    }
}