package com.msmith.rpgai.courses;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.rpgai.paths.PathFinder.Path;

public class GatherItemCourse extends BaseReferenceCounted implements Course {

  private final Path path;

  public GatherItemCourse(Path path) {
    this.path = path;
  }

  public Path path() {
    return path;
  }
}
