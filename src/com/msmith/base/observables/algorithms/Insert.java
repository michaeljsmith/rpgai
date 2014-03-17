package com.msmith.base.observables.algorithms;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.observables.MutableCollection;

public class Insert<T extends ReferenceCounted> extends BaseReferenceCounted {

  private final MutableCollection<T> collection;
  private final T item;

  public Insert(MutableCollection<T> collection, T item) {
    this.collection = addChild(collection);
    this.item = addChild(item);

    collection.add(item);
  }

  @Override
  protected void cleanUp() {
    collection.remove(item);

    super.cleanUp();
  }

  public static <T extends ReferenceCounted> ReferenceCounted insert(
      MutableCollection<T> collection, T item) {

    return new Insert<T>(collection, item);
  }
}
