package com.msmith.rpgai.observables;

import java.util.Set;

class ObserverSet<T> {

  public interface Notifier<T> {
    void notify(T observer);
  }

  public static <T> ObserverSet<T> newSet() {
    return new ObserverSet<T>();
  }

  private Set<T> observers;

  public void forEach(Notifier<T> notifier) {
    for (T observer: observers) {
      notifier.notify(observer);
    }
  }

  public void add(T observer) {
    observers.add(observer);
  }
 
  public void remove(T observer) {
    observers.remove(observer);
  }
}
