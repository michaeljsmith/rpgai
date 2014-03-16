package com.msmith.base.observables;

import com.msmith.base.ReferenceCounted;

public interface Collection<T> extends Iterable<T>, ReferenceCounted {
  interface Observer<T> {
    void onAddItem(T item);
    void onRemoveItem(T item);
  }

  void cleanUp();
  void addObserver(Observer<T> observer);
  void removeObserver(Observer<T> observer);
}
