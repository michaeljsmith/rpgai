package com.msmith.base;

import com.google.common.base.Preconditions;

public abstract class BaseReferenceCounted implements ReferenceCounted {

  private int refCount = 0;

  @Override
  public final void incRef() {
    ++refCount;
  }

  @Override
  public final void decRef() {
    if (--refCount == 0) {
      cleanUp();
    }
  }

  protected abstract void cleanUp();

  @Override
  protected void finalize() throws Throwable {
    Preconditions.checkState(refCount == 0);
    super.finalize();
  }
}
