package com.msmith.rpgai.paths;

import com.msmith.base.ReferenceCounted;
import com.msmith.base.observables.Collection;

public interface PathFinder {

  public interface Path extends ReferenceCounted {
  }

  public interface Request extends ReferenceCounted {
    Collection<Path> paths();
  }

  Request request();
}
