package com.msmith.base;

public interface UniversalProvider {
  <T> T get(Class<T> clazz);
}
