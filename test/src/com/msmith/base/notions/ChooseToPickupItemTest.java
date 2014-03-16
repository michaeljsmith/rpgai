package com.msmith.base.notions;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.msmith.base.UniversalProvider;
import com.msmith.base.UniversalProviders;
import com.msmith.base.observables.MutableCollection;
import com.msmith.base.reflection.TypeLiteral;
import com.msmith.rpgai.courses.Course;
import com.msmith.rpgai.notions.ItemAvailableNotion;
import com.msmith.rpgai.notions.NotionCollectionProvider;
import com.msmith.rpgai.notions.NotionCollectionProviders;
import com.msmith.rpgai.paths.PathFinder;
import com.msmith.rpgai.paths.PathFinder.Request;
import com.msmith.rpgai.reasoning.HumanBehaviourModule;

public class ChooseToPickupItemTest {

  public class MockPathFindRequest implements Request {

  }

  @Test
  public void test() {
    NotionCollectionProvider provider = NotionCollectionProviders.newProvider();
    PathFinder pathFinder = mock(PathFinder.class);
    PathFinder.Request pathFindRequest = new MockPathFindRequest();;
    when(pathFinder.request()).thenReturn(pathFindRequest);
    new HumanBehaviourModule(provider, pathFinder).configure();

    MutableCollection<ItemAvailableNotion<String>> itemAvailableNotions =
            provider.get(new TypeLiteral<ItemAvailableNotion<String>>() {});
    itemAvailableNotions.add(new ItemAvailableNotion<String>());

    verify(pathFinder.request());

    pathFindRequest.addResult();

    MutableCollection<Course> courses = provider.get(new TypeLiteral<Course>() {});
    Iterator<Course> courseIterator = courses.iterator();
    Course course = courseIterator.next();
    assertTrue(course.isDone());
  }
}
