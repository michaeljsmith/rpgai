package com.msmith.base;

import com.msmith.base.reflection.TypeLiteral;

public interface UniversalProvider {
  <T> T get(TypeLiteral<T> type);
}
