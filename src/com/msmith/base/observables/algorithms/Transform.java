package com.msmith.base.observables.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import com.msmith.base.ReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.BaseCollection;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.ObserverSet;

public class Transform<T extends ReferenceCounted, U extends ReferenceCounted> extends
    BaseCollection<U> {

  private final Function<T, U> function;
  private final Map<T, U> sourceDestinationMap = new HashMap<T, U>();
  private final ObserverSet<Observer<U>> observers = ObserverSet.newSet();

  private final Observer<T> observer = new Observer<T>() {

    @Override
    public void onAddItem(T item) {
      final U addedItem = addCorrespondingItem(item);

      observers.notifyAll(new ObserverSet.Notifier<Observer<U>>() {

        @Override
        public void notify(Observer<U> observer) {
          observer.onRemoveItem(addedItem);
        }});
    }

    @Override
    public void onRemoveItem(T item) {
      final U removedItem = removeCorrespondingItem(item);

      observers.notifyAll(new ObserverSet.Notifier<Observer<U>>() {

        @Override
        public void notify(Observer<U> observer) {
          observer.onRemoveItem(removedItem);
        }});

      removedItem.decRef();
    }
  };
  private Collection<T> source;

  public Transform(Collection<T> source, Function<T, U> function) {
    this.source = addChild(source);
    source.addObserver(observer);
    this.function = function;

    for (T sourceItem : source) {
      addCorrespondingItem(sourceItem);
    }
  }

  @Override
  public void cleanUp() {
    source.removeObserver(observer);

    for (U item : sourceDestinationMap.values()) {
      item.decRef();
    }

    observers.cleanUp();

    super.cleanUp();
  }

  public static <T extends ReferenceCounted, U extends ReferenceCounted> Collection<U> transform(
      Collection<T> source, Function<T, U> function) {

    return new Transform<T, U>(source, function);
  }

  @Override
  public void addObserver(Observer<U> observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer<U> observer) {
    observers.remove(observer);
  }

  @Override
  public Iterator<U> iterator() {
    return sourceDestinationMap.values().iterator();
  }

  private U addCorrespondingItem(final T sourceItem) {
    U newItem = ReferenceCounting.incRef(function.apply(sourceItem));

    Preconditions.checkArgument(null == sourceDestinationMap.put(sourceItem, newItem),
        new Object() {
      @Override
      public String toString() {
        return "Item already in collection: " + sourceItem;
      }
    });

    return newItem;
  }

  private U removeCorrespondingItem(final T sourceItem) {
    U item = sourceDestinationMap.remove(sourceItem);

    Preconditions.checkArgument(null != item,
        new Object() {
      @Override
      public String toString() {
        return "Non-existent item removed: " + sourceItem;
      }
    });

    return item;
  }
}
