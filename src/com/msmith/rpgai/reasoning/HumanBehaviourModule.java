package com.msmith.rpgai.reasoning;

import static com.msmith.base.observables.algorithms.Flatten.flatten;
import static com.msmith.base.observables.algorithms.InsertAll.insertAll;
import static com.msmith.base.observables.algorithms.Transform.transform;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Function;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.MutableCollection;
import com.msmith.base.reflection.TypeLiteral;
import com.msmith.rpgai.courses.Course;
import com.msmith.rpgai.courses.GatherItemCourse;
import com.msmith.rpgai.notions.ItemAvailableNotion;
import com.msmith.rpgai.notions.NotionCollectionProvider;
import com.msmith.rpgai.paths.PathFinder;

public class HumanBehaviourModule {
  private final NotionCollectionProvider provider;
  private final PathFinder pathFinder;

  private Set<ReferenceCounted> handles = new HashSet<ReferenceCounted>();

  public HumanBehaviourModule(NotionCollectionProvider provider, PathFinder pathFinder) {
    this.provider = provider;
    this.pathFinder = pathFinder;

    Collection<ItemAvailableNotion<String>> itemsAvailable = provider
            .get(new TypeLiteral<ItemAvailableNotion<String>>() {});
    MutableCollection<Course> courses = provider.get(new TypeLiteral<Course>() {});

    // For all available items we know of, search for paths to them. For any
    // paths, add a possible course of action.
    handles.add(insertAll(courses, flatten(transform(itemsAvailable,
            new Function<ItemAvailableNotion<String>, Collection<Course>>() {
              @Override
              public Collection<Course> apply(ItemAvailableNotion<String> itemAvailable) {

                PathFinder.Request pathFinderRequest = pathFinder.request();
                return transform(pathFinderRequest.paths(),
                        new Function<PathFinder.Path, Course>() {
                          @Override
                          public Course apply(PathFinder.Path path) {
                            return new GatherItemCourse(path);
                          }
                        });
              }
            }))));
  }
}
