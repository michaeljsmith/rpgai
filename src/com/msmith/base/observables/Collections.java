package com.msmith.base.observables;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Preconditions;

import com.msmith.base.ReferenceCounted;

public class Collections {
  public static final class MutableCollectionImpl<T extends ReferenceCounted>
      extends BaseCollection<T> implements MutableCollection<T> {

    private final Set<T> items = new HashSet<T>();
    private final ObserverSet<Observer<T>> observers = ObserverSet.newSet();

    @Override
    public Iterator<T> iterator() {
      return items.iterator();
    }

    @Override
    public void cleanUp() {
      observers.cleanUp();
      super.cleanUp();
    }

    @Override
    public void addObserver(Observer<T> observer) {
      observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<T> observer) {
      observers.remove(observer);
    }

    @Override
    public void add(final T item) {
      Preconditions.checkArgument(items.add(item), new Object() {
        @Override
        public String toString() {
          return "Duplicate item: " + item;
        }
      });

      observers.notifyAll(new ObserverSet.Notifier<Observer<T>>() {
        @Override
        public void notify(Observer<T> observer) {
          observer.onAddItem(item);
        }
      });
    }

    @Override
    public void remove(final T item) {
      Preconditions.checkArgument(items.remove(item), new Object() {
        @Override
        public String toString() {
          return "Non-existent item removed: " + item;
        }
      });

      observers.notifyAll(new ObserverSet.Notifier<Observer<T>>() {
        @Override
        public void notify(Observer<T> observer) {
          observer.onRemoveItem(item);
        }
      });
    }
  }

  private Collections() {}

  public static <T extends ReferenceCounted> MutableCollection<T> newCollection() {
    return new MutableCollectionImpl<T>();
  }
}
