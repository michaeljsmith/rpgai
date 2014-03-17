package com.msmith.base;

import static org.junit.Assert.*;

import com.msmith.base.reflection.TypeLiteral;

import org.junit.Test;

import java.util.ArrayList;

public class UniversalProvidersTest {

  @Test
  public void test() {
    final String STRING = "Foo";

    UniversalProvider provider = UniversalProviders.newSimpleUniversalProvider();
    ArrayList<String> list = provider.get(new TypeLiteral<ArrayList<String>>() {});
    list.add(STRING);

    ArrayList<String> list2 = provider.get(new TypeLiteral<ArrayList<String>>() {});
    assertEquals(1, list2.size());
    assertEquals(STRING, list2.get(0));
  }
}
