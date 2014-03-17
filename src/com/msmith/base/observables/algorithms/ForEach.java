package com.msmith.base.observables.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.MutableCollection;

public class ForEach<T> extends BaseReferenceCounted {

  private final Collection<T> collection;
  private final Function<T, ReferenceCounted> function;
  private final Map<T, ReferenceCounted> itemObjectMap = new HashMap<T, ReferenceCounted>();

  private final Collection.Observer<T> observer = new Collection.Observer<T>() {

    @Override
    public void onAddItem(T item) {
      createObject(item);
    }

    @Override
    public void onRemoveItem(T item) {
      removeObject(item);
    }
  };

  private final Set<ReferenceCounted> children = new HashSet<ReferenceCounted>();

  public ForEach(Collection<T> collection, Function<T, ReferenceCounted> function) {
    this.collection = ReferenceCounting.incRef(collection);
    this.function = function;
  }

  @Override
  protected void cleanUp() {
    for (ReferenceCounted object : itemObjectMap.values()) {
      object.decRef();
    }
  }

  public static <T> ReferenceCounted forEach(
          Collection<T> collection, Function<T, ReferenceCounted> function) {
    return new ForEach<T>(collection, function);
  }

  private void createObject(final T item) {
    Preconditions.checkArgument(null == itemObjectMap.put(item, function.apply(item)),
        new Object() {
      @Override
      public String toString() {
        return "Item already in collection: " + item;
      }
    });
  }

  private void removeObject(final T item) {
    Preconditions.checkArgument(null != itemObjectMap.remove(item), new Object() {
      @Override
      public String toString() {
        return "Non-existent item removed: " + item;
      }
    });
  }
}
