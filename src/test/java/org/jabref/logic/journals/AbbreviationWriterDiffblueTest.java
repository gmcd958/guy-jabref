package org.jabref.logic.journals;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mockStatic;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AbbreviationWriterDiffblueTest {
  /**
  * Method under test: {@link AbbreviationWriter#writeOrCreate(Path, List)}
  */
  @Test
  void testWriteOrCreate() throws IOException {
    try (MockedStatic<Files> mockFiles = mockStatic(Files.class)) {
      // Arrange
      mockFiles.when(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)))
          .thenReturn(new ByteArrayOutputStream(1));
      Path path = Paths.get(System.getProperty("java.io.tmpdir"), "");

      // Act
      AbbreviationWriter.writeOrCreate(path, new ArrayList<>());

      // Assert
      mockFiles.verify(() -> Files.newOutputStream(Mockito.<Path>any(), isA(OpenOption[].class)));
    }
  }
}

