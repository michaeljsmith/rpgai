package com.msmith.base.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeLiteral<T> {
  private final Type type;

  protected TypeLiteral() {
    Type superclass = getClass().getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
  }

  public Type type() {
    return type;
  }

  public Class<T> getClassObject() {
    Class<?> rawType = type instanceof Class<?> ?
            (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType();

    @SuppressWarnings("unchecked")
    Class<T> result = (Class<T>) rawType;

    return result;
  }
}
