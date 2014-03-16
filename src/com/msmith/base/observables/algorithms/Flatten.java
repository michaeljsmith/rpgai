package com.msmith.base.observables.algorithms;

import java.util.Iterator;

import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.BaseCollection;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.ObserverSet;

public class Flatten<T> extends BaseCollection<T> {

  private static class IteratorImpl<T> implements Iterator<T> {

    private final Iterator<Collection<T>> collectionIterator;
    private Iterator<T> iterator;
    private T nextItem;

    public IteratorImpl(Iterable<Collection<T>> collectionIterable) {
      this.collectionIterator = collectionIterable.iterator();
      this.iterator = null;
      advance();
    }

    @Override
    public boolean hasNext() {
      return nextItem != null;
    }

    @Override
    public T next() {
      T item = nextItem;
      advance();
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Collection is immutable");
    }

    private void advance() {
      while (iterator == null || !iterator.hasNext()) {
        if (!collectionIterator.hasNext()) {
          nextItem = null;
          return;
        }

        Collection<T> collection = collectionIterator.next();
        iterator = collection.iterator();
      }

      nextItem = iterator.next();
    }
  }

  private final Collection<Collection<T>> collections;
  private ObserverSet<Observer<T>> observers = ObserverSet.newSet();

  private final Observer<Collection<T>> collectionObserver = new Observer<Collection<T>>() {

    @Override
    public void onAddItem(Collection<T> collection) {
      observeCollection(collection);
    }

    @Override
    public void onRemoveItem(Collection<T> collection) {
      unobserveCollection(collection);
    }
  };

  private final Observer<T> observer = new Observer<T>() {

    @Override
    public void onAddItem(final T item) {
      observers.notifyAll(new ObserverSet.Notifier<Observer<T>>() {

        @Override
        public void notify(Observer<T> observer) {
          observer.onAddItem(item);
        }
      });
    }

    @Override
    public void onRemoveItem(final T item) {
      observers.notifyAll(new ObserverSet.Notifier<Observer<T>>() {

        @Override
        public void notify(Observer<T> observer) {
          observer.onAddItem(item);
        }
      });
    }
  };

  public Flatten(Collection<Collection<T>> collections) {
    this.collections = ReferenceCounting.incRef(collections);

    collections.addObserver(collectionObserver);
    for (Collection<T> collection : collections) {
      observeCollection(collection);
    }
  }

  @Override
  public void cleanUp() {
    for (Collection<T> collection : collections) {
      unobserveCollection(collection);
    }

    collections.removeObserver(collectionObserver);
    collections.decRef();

    observers.cleanUp();
  }

  public static <T> Collection<T> flatten(Collection<Collection<T>> courses) {
    return new Flatten<T>(courses);
  }

  @Override
  public Iterator<T> iterator() {
    return new IteratorImpl<T>(collections);
  }

  @Override
  public void addObserver(Observer<T> observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer<T> observer) {
    observers.remove(observer);
  }

  private void observeCollection(Collection<T> collection) {
    collection.addObserver(observer);
  }

  private void unobserveCollection(Collection<T> collection) {
    collection.removeObserver(observer);
  }
}
