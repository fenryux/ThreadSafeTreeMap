package org.example;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.StampedLock;

public class ThreadSafeTreeMap<K,V> extends TreeMap<K,V> implements Map<K,V> {

    private final StampedLock stampedLock = new StampedLock();

    @Override
    public V get(Object key) {
        long stamp = stampedLock.tryOptimisticRead();

        try{
            if(stampedLock.validate(stamp)){
                stamp = stampedLock.readLock();
                return super.get(key);
            }
        }
        finally {
            stampedLock.unlockRead(stamp);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        long stamp = stampedLock.writeLock();
        try{
            if(stampedLock.validate(stamp)){
                return super.put(key, value);
            }
        }
        finally {
            stampedLock.unlockWrite(stamp);
        }
        return null;
    }
}
