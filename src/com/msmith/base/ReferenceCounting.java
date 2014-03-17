package com.msmith.base;

import java.util.List;

public class ReferenceCounting {
  public static <T extends ReferenceCounted> T incRef(T referee) {
    referee.incRef();
    return referee;
  }

  public static ReferenceCounted compoundReferenceCounted(final List<ReferenceCounted> children) {
    return new BaseReferenceCounted() {
      @Override
      protected void cleanUp() {
        for (ReferenceCounted child : children) {
          child.decRef();
        }

        super.cleanUp();
      }
    };
  }
}
