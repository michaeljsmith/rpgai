package com.msmith.base.observables;

public interface MutableValue<T> extends Value<T> {
  void set(T newValue);
}
