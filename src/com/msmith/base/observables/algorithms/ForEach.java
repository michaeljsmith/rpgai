package com.msmith.base.observables.algorithms;

import com.google.common.base.Function;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.observables.Collection;

public class ForEach<T extends ReferenceCounted> extends BaseReferenceCounted {

  public static <T extends ReferenceCounted> ReferenceCounted forEach(
          Collection<T> collection, Function<T, ReferenceCounted> function) {

    return new Transform<T, ReferenceCounted>(collection, function);
  }
}
