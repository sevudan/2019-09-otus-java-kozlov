package ru.otus.hw11.cache;

public interface HwListener<K, V> {
  void notify(K key, V value, CacheAction action);
}
