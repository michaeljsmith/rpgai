package com.msmith.base;

import java.util.HashMap;
import java.util.Map;

import com.msmith.base.reflection.TypeLiteral;

public class UniversalProviders {
  private UniversalProviders() {}

  public static UniversalProvider newSimpleUniversalProvider() {
    return new UniversalProvider() {
      private final Map<TypeLiteral<?>, Object> classObjectMap =
              new HashMap<TypeLiteral<?>, Object>();

      @Override
      public <T> T get(TypeLiteral<T> type) {
        Class<T> clazz = type.getClassObject();

        Object object = classObjectMap.get(type);
        if (object != null) {
          return clazz.cast(object);
        }

        T newInstance;
        try {
          newInstance = clazz.newInstance();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }

        classObjectMap.put(type, newInstance);
        return newInstance;
      }
    };
  }
}
