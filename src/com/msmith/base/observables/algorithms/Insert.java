package com.msmith.base.observables.algorithms;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.MutableCollection;

public class Insert<T> extends BaseReferenceCounted {

  private final MutableCollection<T> collection;
  private final T item;

  public Insert(MutableCollection<T> collection, T item) {
    this.collection = ReferenceCounting.incRef(collection);
    this.item = item;

    collection.add(item);
  }

  @Override
  protected void cleanUp() {
    collection.remove(item);
    collection.decRef();
  }

  public static <T> ReferenceCounted insert(MutableCollection<T> collection, T item) {
    return new Insert<T>(collection, item);
  }
}
