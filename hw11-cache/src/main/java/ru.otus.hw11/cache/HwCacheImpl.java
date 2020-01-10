package ru.otus.hw11.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;

public class HwCacheImpl<K, V> implements HwCache<K, V> {

  private static Logger logger = LoggerFactory.getLogger(HwCacheImpl.class);

  private final Map<K, V> cacheMap = new WeakHashMap<>();

  private final List<WeakReference<HwListener<K, V>>> arrayListeners = new ArrayList<>();

  private final ReferenceQueue<HwListener<K, V>> queue = new ReferenceQueue<>();

  private boolean isListenersEmpty() {
    return arrayListeners.isEmpty();
  }

  public boolean isEmpty(){
    return cacheMap.isEmpty();
  }

  public int cacheSize(){
    return cacheMap.size();
  }

  private void onNotify(K key, V value, CacheAction action) {
    arrayListeners.stream().forEach(listener -> {
      try {
        listener.get().notify(key, value, action);
      } catch (Exception ex){
        logger.error("ERROR: Listener notify exception!", ex);
      }
    });
  }

  private WeakReference<HwListener<K, V>> getReferenceListener(HwListener<K, V> listener) {
    int listenerHashCode = listener.hashCode();
    return arrayListeners.stream().filter(val -> val.get().hashCode() == listenerHashCode)
            .findFirst().orElse(null);
  }

  @Override
  public void put(K key, V value) {
    cacheMap.put(key, value);
    if (!isListenersEmpty()) {
      onNotify(key, value, CacheAction.ADD_INTO_CACHE);
    }
  }

  @Override
  public void remove(K key) {
    V value = cacheMap.get(key);
    cacheMap.remove(key);
    onNotify(key, value, CacheAction.REMOVE_FROM_CACHE);
  }

  @Override
  public V get(K key) {
    return cacheMap.get(key);
  }

  @Override
  public void addListener(HwListener<K, V> listener) {
    WeakReference<HwListener<K, V>> listenerWeakRef = new WeakReference<>(listener, queue);
    arrayListeners.add(listenerWeakRef);
  }

  @Override
  public void removeListener(HwListener<K, V> listener) {
    if (!arrayListeners.isEmpty()) {
      arrayListeners.remove(getReferenceListener(listener));
    } else {
      throw new IllegalArgumentException("Listener is missing!");
    }
  }
}