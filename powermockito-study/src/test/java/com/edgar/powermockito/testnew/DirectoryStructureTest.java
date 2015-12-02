package com.edgar.powermockito.testnew;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

@RunWith(PowerMockRunner.class)
 @PrepareForTest(DirectoryStructure.class)
 public class DirectoryStructureTest { 
    @Test
    public void createDirectoryStructureWhenPathDoesntExist() throws Exception { 
        final String directoryPath = "mocked path"; 

        File directoryMock = mock(File.class);

        // This is how you tell PowerMockito to mock construction of a new File. 
        whenNew(File.class).withArguments(directoryPath).thenReturn(directoryMock); 

        // Standard expectations 
        when(directoryMock.exists()).thenReturn(false); 
        when(directoryMock.mkdirs()).thenReturn(true); 

        assertTrue(new DirectoryStructure().create(directoryPath));

        // Optionally verify that a new File was "created". 
        verifyNew(File.class).withArguments(directoryPath); 
    }

  @Test
  public void createTestObj() throws Exception {

    TestObj directoryMock = mock(TestObj.class);

    whenNew(TestObj.class).withNoArguments().thenReturn(directoryMock);

    // Standard expectations
    when(directoryMock.isResult()).thenReturn(true);

    assertTrue(new DirectoryStructure().createObj());

    // Optionally verify that a new File was "created".
    verifyNew(TestObj.class).withNoArguments();
  }
}