package com.msmith.base.observables.algorithms;

import static com.msmith.base.observables.algorithms.ForEach.forEach;
import static com.msmith.base.observables.algorithms.Insert.insert;

import com.google.common.base.Function;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.MutableCollection;

public class InsertAll {
  private InsertAll() {}

  public static <T extends ReferenceCounted> ReferenceCounted insertAll(
      final MutableCollection<T> destination, Collection<T> source) {

    return forEach(source, new Function<T, ReferenceCounted>() {
      @Override
      public ReferenceCounted apply(T item) {
        return insert(destination, item);
      }
    });
  }
}
