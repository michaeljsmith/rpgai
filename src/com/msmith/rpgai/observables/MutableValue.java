package com.msmith.rpgai.observables;

public interface MutableValue<T> extends Value<T> {
  void set(T newValue);
}
