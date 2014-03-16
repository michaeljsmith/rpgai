package com.msmith.base.observables.algorithms;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Function;
import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.MutableCollection;

public class ForEach<T> extends BaseReferenceCounted {

  private final Collection<T> collection;
  private final Function<T, ReferenceCounted> function;

  private final Set<ReferenceCounted> children = new HashSet<ReferenceCounted>();

  public ForEach(Collection<T> collection, Function<T, ReferenceCounted> function) {
    this.collection = ReferenceCounting.incRef(collection);
    this.function = function;
  }

  public static <T> ReferenceCounted forEach(
          Collection<T> collection, Function<T, ReferenceCounted> function) {
    return new ForEach<T>(collection, function);
  }
}
