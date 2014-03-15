package com.msmith.base.observables;

public interface Collection<T> extends Iterable<T> {
  interface Observer<T> {
    void onAddItem(T item);
    void onRemoveItem(T item);
  }
  
  void addObserver(Observer<T> observer);
  void removeObserver(Observer<T> observer);
}
