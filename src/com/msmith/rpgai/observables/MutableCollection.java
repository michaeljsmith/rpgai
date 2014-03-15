package com.msmith.rpgai.observables;

public interface MutableCollection<T> extends Collection<T> {
  void add(T item);
  void remove(T item);
}
