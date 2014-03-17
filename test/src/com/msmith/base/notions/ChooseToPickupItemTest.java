package com.msmith.base.notions;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import org.junit.Test;

import com.msmith.base.BaseReferenceCounted;
import com.msmith.base.ReferenceCounted;
import com.msmith.base.ReferenceCounting;
import com.msmith.base.observables.Collection;
import com.msmith.base.observables.Collections;
import com.msmith.base.observables.MutableCollection;
import com.msmith.base.reflection.TypeLiteral;
import com.msmith.rpgai.courses.Course;
import com.msmith.rpgai.notions.ItemAvailableNotion;
import com.msmith.rpgai.notions.NotionCollectionProvider;
import com.msmith.rpgai.notions.NotionCollectionProviders;
import com.msmith.rpgai.paths.PathFinder;
import com.msmith.rpgai.reasoning.HumanBehaviourModule;

public class ChooseToPickupItemTest {

  public class MockPath extends BaseReferenceCounted implements PathFinder.Path {
  }

  public class MockPathFindRequest extends BaseReferenceCounted implements PathFinder.Request {

    private final MutableCollection<PathFinder.Path> paths =
        addChild(Collections.<PathFinder.Path>newCollection());

    @Override
    public Collection<PathFinder.Path> paths() {
      return paths;
    }

    public void addResult() {
      paths.add(new MockPath());
    }
  }

  @Test
  public void test() {
    NotionCollectionProvider provider = NotionCollectionProviders.newProvider();
    PathFinder pathFinder = mock(PathFinder.class);
    MockPathFindRequest pathFindRequest = new MockPathFindRequest();
    when(pathFinder.request()).thenReturn(pathFindRequest);
    ReferenceCounted behaviour = ReferenceCounting.incRef(HumanBehaviourModule.configure(provider, pathFinder));

    MutableCollection<ItemAvailableNotion<String>> itemAvailableNotions =
            provider.get(new TypeLiteral<ItemAvailableNotion<String>>() {});
    itemAvailableNotions.add(new ItemAvailableNotion<String>());

    verify(pathFinder).request();

    pathFindRequest.addResult();

    MutableCollection<Course> courses = provider.get(new TypeLiteral<Course>() {});
    Iterator<Course> courseIterator = courses.iterator();
    courseIterator.next();
    assertFalse(!courseIterator.hasNext());

    behaviour.decRef();
  }
}
