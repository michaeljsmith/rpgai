package com.msmith.base;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseReferenceCounted implements ReferenceCounted {

  private int refCount = 0;
  private List<ReferenceCounted> children = new ArrayList<ReferenceCounted>();

  @Override
  public final void incRef() {
    ++refCount;
  }

  @Override
  public final void decRef() {
    Preconditions.checkState(refCount > 0, "decRef() called too often.");
    if (--refCount == 0) {
      cleanUp();
    }
  }

  protected void cleanUp() {}

  protected <T extends ReferenceCounted> T addChild(T child) {
    ReferenceCounting.incRef(child);
    children.add(child);
    return child;
  }

  @Override
  protected void finalize() throws Throwable {
    Preconditions.checkState(refCount == 0);
    super.finalize();
  }
}
