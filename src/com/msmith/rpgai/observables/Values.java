package com.msmith.rpgai.observables;

public class Values {

  private static final class MutableValueImpl<T> implements MutableValue<T> {
    private T value;
    private final ObserverSet<Observer<T>> observers = ObserverSet.newSet();

    public MutableValueImpl(T initialValue) {
      value = initialValue;
    }

    @Override
    public T get() {
      return value; 
    }

    @Override
    public void addObserver(com.msmith.rpgai.observables.Value.Observer<T> observer) {
      observers.add(observer);
    }

    @Override
    public void removeObserver(com.msmith.rpgai.observables.Value.Observer<T> observer) {
      observers.remove(observer);
    }

    @Override
    public void set(final T newValue) {
      final T oldValue = value;
      value = newValue;
      observers.forEach(new ObserverSet.Notifier<Observer<T>>() {
        @Override
        public void notify(Observer<T> observer) {
          observer.onChange(oldValue, newValue);
        }
      });
    }
  }

  private Values() {}
  
  public static <T> MutableValue<T> newValue(T initialValue) {
    return new MutableValueImpl<T>(initialValue);
  }
}
