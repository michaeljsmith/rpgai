package com.msmith.rpgai.paths;

import com.msmith.base.observables.Collection;

public interface PathFinder {

  public interface Path {
  }

  public interface Request {
    Collection<Path> paths();
  }

  Request request();
}
