package com.msmith.base;

import java.util.HashMap;
import java.util.Map;

public class UniversalProviders {
  private UniversalProviders() {}

  public static UniversalProvider newSimpleUniversalProvider() {
    return new UniversalProvider() {
      private final Map<Class<?>, Object> classObjectMap = new HashMap<Class<?>, Object>();

      @Override
      public <T> T get(Class<T> clazz) {
        Object object = classObjectMap.get(clazz);
        if (object != null) {
          return clazz.cast(object);
        }

        T newInstance;
        try {
          newInstance = clazz.newInstance();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }

        classObjectMap.put(clazz, newInstance);
        return newInstance;
      }
    };
  }
}
