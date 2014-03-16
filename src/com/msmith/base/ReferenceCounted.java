package com.msmith.base;

public interface ReferenceCounted {
  void incRef();
  void decRef();
}
