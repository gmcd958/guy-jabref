package org.jabref.logic.layout.format;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AuthorAndToSemicolonReplacerDiffblueTest {
  /**
  * Method under test: {@link AuthorAndToSemicolonReplacer#format(String)}
  */
  @Test
  void testFormat() {
    // Arrange, Act and Assert
    assertEquals("Field Text", (new AuthorAndToSemicolonReplacer()).format("Field Text"));
    assertEquals("; ", (new AuthorAndToSemicolonReplacer()).format(" and "));
    assertEquals("; ; ", (new AuthorAndToSemicolonReplacer()).format(" and  and "));
    assertEquals("; ; ", (new AuthorAndToSemicolonReplacer()).format(" and ; "));
    assertEquals("; Field Text", (new AuthorAndToSemicolonReplacer()).format(" and Field Text"));
    assertEquals("; 42", (new AuthorAndToSemicolonReplacer()).format(" and 42"));
    assertEquals("; ; ", (new AuthorAndToSemicolonReplacer()).format(";  and "));
    assertEquals("Field Text; ", (new AuthorAndToSemicolonReplacer()).format("Field Text and "));
    assertEquals("42; ", (new AuthorAndToSemicolonReplacer()).format("42 and "));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format(" and  and  and "));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format(" and  and ; "));
    assertEquals("; ; Field Text", (new AuthorAndToSemicolonReplacer()).format(" and  and Field Text"));
    assertEquals("; ; 42", (new AuthorAndToSemicolonReplacer()).format(" and  and 42"));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format(" and ;  and "));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format(" and ; ; "));
    assertEquals("; ; Field Text", (new AuthorAndToSemicolonReplacer()).format(" and ; Field Text"));
    assertEquals("; ; 42", (new AuthorAndToSemicolonReplacer()).format(" and ; 42"));
    assertEquals("; Field Text; ", (new AuthorAndToSemicolonReplacer()).format(" and Field Text and "));
    assertEquals("; Field Text; ", (new AuthorAndToSemicolonReplacer()).format(" and Field Text; "));
    assertEquals("; Field TextField Text", (new AuthorAndToSemicolonReplacer()).format(" and Field TextField Text"));
    assertEquals("; Field Text42", (new AuthorAndToSemicolonReplacer()).format(" and Field Text42"));
    assertEquals("; 42; ", (new AuthorAndToSemicolonReplacer()).format(" and 42 and "));
    assertEquals("; 42; ", (new AuthorAndToSemicolonReplacer()).format(" and 42; "));
    assertEquals("; 42Field Text", (new AuthorAndToSemicolonReplacer()).format(" and 42Field Text"));
    assertEquals("; 4242", (new AuthorAndToSemicolonReplacer()).format(" and 4242"));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format(";  and  and "));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format(";  and ; "));
    assertEquals("; ; Field Text", (new AuthorAndToSemicolonReplacer()).format(";  and Field Text"));
    assertEquals("; ; 42", (new AuthorAndToSemicolonReplacer()).format(";  and 42"));
    assertEquals("; ; ; ", (new AuthorAndToSemicolonReplacer()).format("; ;  and "));
    assertEquals("; Field Text; ", (new AuthorAndToSemicolonReplacer()).format("; Field Text and "));
    assertEquals("; 42; ", (new AuthorAndToSemicolonReplacer()).format("; 42 and "));
    assertEquals("Field Text; ; ", (new AuthorAndToSemicolonReplacer()).format("Field Text and  and "));
    assertEquals("Field Text; ; ", (new AuthorAndToSemicolonReplacer()).format("Field Text and ; "));
    assertEquals("Field Text; Field Text", (new AuthorAndToSemicolonReplacer()).format("Field Text and Field Text"));
    assertEquals("Field Text; 42", (new AuthorAndToSemicolonReplacer()).format("Field Text and 42"));
    assertEquals("Field Text; ; ", (new AuthorAndToSemicolonReplacer()).format("Field Text;  and "));
    assertEquals("Field TextField Text; ", (new AuthorAndToSemicolonReplacer()).format("Field TextField Text and "));
    assertEquals("Field Text42; ", (new AuthorAndToSemicolonReplacer()).format("Field Text42 and "));
    assertEquals("42; ; ", (new AuthorAndToSemicolonReplacer()).format("42 and  and "));
    assertEquals("42; ; ", (new AuthorAndToSemicolonReplacer()).format("42 and ; "));
    assertEquals("42; Field Text", (new AuthorAndToSemicolonReplacer()).format("42 and Field Text"));
    assertEquals("42; 42", (new AuthorAndToSemicolonReplacer()).format("42 and 42"));
    assertEquals("42; ; ", (new AuthorAndToSemicolonReplacer()).format("42;  and "));
    assertEquals("42Field Text; ", (new AuthorAndToSemicolonReplacer()).format("42Field Text and "));
    assertEquals("4242; ", (new AuthorAndToSemicolonReplacer()).format("4242 and "));
  }
}

