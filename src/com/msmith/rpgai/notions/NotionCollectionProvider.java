package com.msmith.rpgai.notions;

import com.msmith.base.ReferenceCounted;
import com.msmith.base.observables.MutableCollection;
import com.msmith.base.reflection.TypeLiteral;

public interface NotionCollectionProvider {
  public <T extends ReferenceCounted> MutableCollection<T> get(TypeLiteral<T> typeLiteral);
}
