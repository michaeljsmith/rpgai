package com.msmith.rpgai.observables;

public interface Value<T> {
  interface Observer<T> {
    void onChange(T oldValue, T newValue);
  }
  
  T get();
  void addObserver(Observer<T> observer);
  void removeObserver(Observer<T> observer);
}
