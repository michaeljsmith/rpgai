package com.msmith.base.observables;

import com.msmith.base.ReferenceCounted;

public interface MutableCollection<T extends ReferenceCounted> extends Collection<T> {
  void add(T item);
  void remove(T item);
}
