package com.msmith.base.observables;

public interface MutableCollection<T> extends Collection<T> {
  void add(T item);
  void remove(T item);
}
