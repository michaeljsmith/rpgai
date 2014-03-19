package com.msmith.base.observables.algorithms;

import static com.msmith.base.observables.algorithms.Flatten.flatten;
import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.Collections;
import com.msmith.base.observables.MutableCollection;

public class FlattenTest {

  public class Foo extends BaseReferenceCounted {
  }

  @Test
  public void testEmptyCollection() {
    Collection<Foo> flattenedCollection = flatten(Collections.<Collection<Foo>>newCollection());
    assertFalse(flattenedCollection.iterator().hasNext());
  }

  @Test
  public void testAddItemToCollection() {
    MutableCollection<Collection<Foo>> collections = Collections.newCollection();
    Collection<Foo> flattenedCollection = flatten(collections);
    MutableCollection<Foo> collection = Collections.newCollection();
    collections.add(collection);
    assertFalse(flattenedCollection.iterator().hasNext());

    Foo foo = new Foo();
    collection.add(foo);
    Iterator<Foo> iterator = flattenedCollection.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(foo, iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  public void testAddNonEmptyCollection() {
    MutableCollection<Collection<Foo>> collections = Collections.newCollection();
    Collection<Foo> flattenedCollection = flatten(collections);
    MutableCollection<Foo> collection = Collections.newCollection();

    Foo foo = new Foo();
    collection.add(foo);
    collections.add(collection);

    Iterator<Foo> iterator = flattenedCollection.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(foo, iterator.next());
    assertFalse(iterator.hasNext());
  }
}
