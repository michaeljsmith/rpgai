package com.msmith.rpgai.courses;

import com.msmith.rpgai.paths.PathFinder.Path;

public class GatherItemCourse extends Course {

  private final Path path;

  public GatherItemCourse(Path path) {
    this.path = path;
  }

  public Path path() {
    return path;
  }
}
