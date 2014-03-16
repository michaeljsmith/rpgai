package com.msmith.base.observables.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Function;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.BaseCollection;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.ObserverSet;

public class Transform<T, U> extends BaseCollection<U> {

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
    }
  };
  private Collection<T> source;

  public Transform(Collection<T> source, Function<T, U> function) {
    this.source = ReferenceCounting.incRef(source);
    source.addObserver(observer);
    this.function = function;

    for (T sourceItem : source) {
      addCorrespondingItem(sourceItem);
    }
  }

  @Override
  public void cleanUp() {
    source.removeObserver(observer);
    source.decRef();
    observers.cleanUp();
  }

  public static <T, U> Collection<U> transform(Collection<T> source, Function<T, U> function) {
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

  private U addCorrespondingItem(T sourceItem) {
    U newItem = function.apply(sourceItem);
    sourceDestinationMap.put(sourceItem, newItem);
    return newItem;
  }

  private U removeCorrespondingItem(T sourceItem) {
    return sourceDestinationMap.remove(sourceItem);
  }
}
