package com.msmith.base;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.msmith.base.reflection.TypeLiteral;

public class UniversalProviders {
  private UniversalProviders() {}

  public static UniversalProvider newSimpleUniversalProvider() {
    return new UniversalProvider() {
      private final Map<Type, Object> classObjectMap =
              new HashMap<Type, Object>();

      @Override
      public <T> T get(TypeLiteral<T> typeLiteral) {
        Class<T> clazz = typeLiteral.getClassObject();
        Type type = typeLiteral.type();

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
