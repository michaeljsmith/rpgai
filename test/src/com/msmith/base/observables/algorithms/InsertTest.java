package com.msmith.base.observables.algorithms;

import static org.junit.Assert.*;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.Collections;
import com.msmith.base.observables.MutableCollection;

import org.junit.Test;

import java.util.Iterator;

public class InsertTest {

  public class Foo extends BaseReferenceCounted {

    public final String string;
    public boolean cleanedUp = false;

    public Foo(String string) {
      this.string = string;
    }

    @Override
    protected void cleanUp() {
      cleanedUp = true;
      super.cleanUp();
    }
  }

  @Test
  public void test() {
    final String STRING = "Hello";

    MutableCollection<Foo> collection =
        ReferenceCounting.incRef(Collections.<Foo>newCollection());

    Foo item = new Foo(STRING);
    ReferenceCounted insert = ReferenceCounting.incRef(Insert.insert(collection, item));

    Iterator<Foo> iterator = collection.iterator();
    assertEquals(STRING, iterator.next().string);
    assertFalse(iterator.hasNext());

    assertFalse(item.cleanedUp);

    insert.decRef();

    collection.iterator();
    assertFalse(iterator.hasNext());

    assertTrue(item.cleanedUp);

    collection.decRef();
  }
}
