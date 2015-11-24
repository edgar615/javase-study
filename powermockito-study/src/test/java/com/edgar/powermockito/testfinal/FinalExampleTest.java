package com.edgar.powermockito.testfinal;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
@PrepareForTest(FinalExampleTest.class)
public class FinalExampleTest {
  @Test
  public void finalMethodMock() throws Exception {
    ClassDependency depencency = mock(ClassDependency.class);

    ClassUnderTest underTest = new ClassUnderTest();

    when(depencency.isAlive()).thenReturn(true);

    assertTrue(underTest.callFinalMethod(depencency));
  }
}