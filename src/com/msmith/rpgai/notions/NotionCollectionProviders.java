package com.msmith.rpgai.notions;

import com.msmith.base.UniversalProvider;
import com.msmith.base.UniversalProviders;
import com.msmith.base.observables.Collections;
import com.msmith.base.observables.MutableCollection;
import com.msmith.base.reflection.TypeLiteral;

public class NotionCollectionProviders {
  public static NotionCollectionProvider newProvider() {
    return new NotionCollectionProvider() {
      UniversalProvider provider = UniversalProviders.newSimpleUniversalProvider();

      @Override
      public <T> MutableCollection<T> get(TypeLiteral<T> typeLiteral) {
        return provider.get(new TypeLiteral<Collections.MutableCollectionImpl<T>>() {});
      }
    };
  }
}
