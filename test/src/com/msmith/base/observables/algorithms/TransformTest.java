package com.msmith.base.observables.algorithms;

import static org.junit.Assert.*;
import static com.msmith.base.observables.algorithms.Transform.transform;

import com.google.common.base.Function;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.Collections;
import com.msmith.base.observables.MutableCollection;

import org.junit.Test;

import java.util.Iterator;

public class TransformTest {

  public class Bar extends BaseReferenceCounted {

    public final String string;
    private boolean cleanedUp = false;

    public Bar(String string) {
      this.string = string;
      }

    @Override
    protected void cleanUp() {
      cleanedUp = true;
      super.cleanUp();
    }
  }

  public class Foo extends BaseReferenceCounted {

    public final String string;

    Foo(String string) {
      this.string = string;
    }
  }

  @Test
  public void testAddItem() {
    String STRING = "Hello";

    MutableCollection<Foo> sourceCollection =
        ReferenceCounting.incRef(Collections.<Foo>newCollection());
    Collection<Bar> destinationCollection = testTransform(sourceCollection);

    sourceCollection.add(new Foo(STRING));
    Iterator<Bar> iterator = destinationCollection.iterator();
    assertEquals(STRING, iterator.next().string);
    assertFalse(iterator.hasNext());

    destinationCollection.decRef();
    sourceCollection.decRef();
  }

  @Test
  public void testRemoveItem() {
    String STRING = "Hello";

    MutableCollection<Foo> sourceCollection =
        ReferenceCounting.incRef(Collections.<Foo>newCollection());

    Foo item = new Foo(STRING);
    sourceCollection.add(item);

    Collection<Bar> destinationCollection = testTransform(sourceCollection);
    sourceCollection.remove(item);

    Iterator<Bar> iterator = destinationCollection.iterator();
    assertFalse(iterator.hasNext());

    destinationCollection.decRef();
    sourceCollection.decRef();
  }

  @Test
  public void testInitialItemTransformed() {
    String STRING = "Hello";

    MutableCollection<Foo> sourceCollection =
        ReferenceCounting.incRef(Collections.<Foo>newCollection());
    sourceCollection.add(new Foo(STRING));

    Collection<Bar> destinationCollection = testTransform(sourceCollection);

    Iterator<Bar> iterator = destinationCollection.iterator();
    assertEquals(STRING, iterator.next().string);
    assertFalse(iterator.hasNext());

    destinationCollection.decRef();
    sourceCollection.decRef();
  }

  @Test
  public void testItemIsCleanedUpOnRemove() {
    String STRING = "Hello";

    MutableCollection<Foo> sourceCollection =
        ReferenceCounting.incRef(Collections.<Foo>newCollection());
    Collection<Bar> destinationCollection = testTransform(sourceCollection);

    Foo item = new Foo(STRING);
    sourceCollection.add(item);
    Iterator<Bar> iterator = destinationCollection.iterator();
    Bar object = iterator.next();
    assertFalse(object.cleanedUp);

    sourceCollection.remove(item);
    assertTrue(object.cleanedUp);

    destinationCollection.decRef();
    sourceCollection.decRef();
  }

  @Test
  public void testItemIsCleanedUpOnExit() {
    String STRING = "Hello";

    MutableCollection<Foo> sourceCollection =
        ReferenceCounting.incRef(Collections.<Foo>newCollection());
    Collection<Bar> destinationCollection = testTransform(sourceCollection);

    sourceCollection.add(new Foo(STRING));
    Iterator<Bar> iterator = destinationCollection.iterator();
    Bar object = iterator.next();
    assertFalse(object.cleanedUp);

    destinationCollection.decRef();
    assertTrue(object.cleanedUp);

    sourceCollection.decRef();
  }

  private Collection<Bar> testTransform(Collection<Foo> sourceCollection) {
    Collection<Bar> transformedCollection =
        ReferenceCounting.incRef(transform(sourceCollection, new Function<Foo, Bar>() {
          @Override
          public Bar apply(Foo foo) {
            return new Bar(foo.string);
          }
        }));
    return transformedCollection;
  }
}
