package org.jabref.logic.layout.format;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AuthorAndsCommaReplacerDiffblueTest {
  /**
  * Method under test: {@link AuthorAndsCommaReplacer#format(String)}
  */
  @Test
  void testFormat() {
    // Arrange, Act and Assert
    assertEquals("Field Text", (new AuthorAndsCommaReplacer()).format("Field Text"));
    assertEquals(" & Field Text", (new AuthorAndsCommaReplacer()).format(" and Field Text"));
    assertEquals(",  & Field Text", (new AuthorAndsCommaReplacer()).format(" and  and Field Text"));
  }
}

