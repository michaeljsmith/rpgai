package com.msmith.base.observables;

import java.util.Set;

import com.google.common.base.Preconditions;

class ObserverSet<T> {

  public interface Notifier<T> {
    void notify(T observer);
  }

  public static <T> ObserverSet<T> newSet() {
    return new ObserverSet<T>();
  }

  private Set<T> observers;

  public void notifyAll(Notifier<T> notifier) {
    for (T observer: observers) {
      notifier.notify(observer);
    }
  }

  public void add(final T observer) {
    Preconditions.checkArgument(observers.add(observer), new Object() {
      @Override
      public String toString() {
        return "Duplicate observer: " + observer;
      }
    });
  }
 
  public void remove(final T observer) {
    Preconditions.checkArgument(observers.remove(observer), new Object() {
      @Override
      public String toString() {
        return "Removed non-existent observer: " + observer;
      }
    });
  }
  
  @Override
  protected void finalize() throws Throwable {
    Preconditions.checkState(observers.isEmpty(), new Object() {
      @Override
      public String toString() {
        return "Observers not deregistered: " + observers;
      }
    });
  }
}
