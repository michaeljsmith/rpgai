package com.msmith.base;

public class ReferenceCounting {
  public static <T extends ReferenceCounted> T incRef(T referee) {
    referee.incRef();
    return referee;
  }
}
