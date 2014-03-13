package com.msmith.rpgai.observables;

public class ObservableList<T> {
  public interface Observer<T> {
    void onItemAdded(T item, T nextItem);
    void onItemRemoved(T item, T nextItem);
  }
}
