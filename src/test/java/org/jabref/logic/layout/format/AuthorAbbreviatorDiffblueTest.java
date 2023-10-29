package org.jabref.logic.layout.format;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class AuthorAbbreviatorDiffblueTest {
  /**
  * Method under test: {@link AuthorAbbreviator#format(String)}
  */
  @Test
  void testFormat() {
    // Arrange, Act and Assert
    assertEquals("Text, F.", (new AuthorAbbreviator()).format("Field Text"));
    assertEquals("foo", (new AuthorAbbreviator()).format("foo"));
    assertEquals("others", (new AuthorAbbreviator()).format(" and others"));
    assertEquals("", (new AuthorAbbreviator()).format(" AND "));
    assertEquals("{", (new AuthorAbbreviator()).format("{"));
    assertEquals("", (new AuthorAbbreviator()).format(";"));
    assertEquals("", (new AuthorAbbreviator()).format(","));
    assertEquals("", (new AuthorAbbreviator()).format(",;-"));
    assertEquals("others and others", (new AuthorAbbreviator()).format(" and others and others"));
    assertEquals("others{", (new AuthorAbbreviator()).format(" and others{"));
    assertEquals("others", (new AuthorAbbreviator()).format(" and others;"));
    assertEquals("others", (new AuthorAbbreviator()).format(" and others,"));
    assertEquals("othersField Text", (new AuthorAbbreviator()).format(" and othersField Text"));
    assertEquals("Text, F. T.", (new AuthorAbbreviator()).format("Field TextField Text"));
    assertEquals("{ AND", (new AuthorAbbreviator()).format("{ AND "));
    assertEquals("{Field Text", (new AuthorAbbreviator()).format("{Field Text"));
    assertEquals(", {.", (new AuthorAbbreviator()).format(",{"));
    assertEquals("", (new AuthorAbbreviator()).format(",,"));
    assertEquals("", (new AuthorAbbreviator()).format(",,;-"));
    assertEquals("Field Text", (new AuthorAbbreviator()).format("Field Text,"));
    assertEquals(", T.", (new AuthorAbbreviator()).format(",TEXT"));
    assertEquals("others and others and others", (new AuthorAbbreviator()).format(" and others and others and others"));
    assertEquals("others, {.", (new AuthorAbbreviator()).format(" and others,{"));
    assertEquals("others", (new AuthorAbbreviator()).format(" and others,,"));
    assertEquals("others", (new AuthorAbbreviator()).format(" and others,,;-"));
    assertEquals("othersField Text", (new AuthorAbbreviator()).format(" and othersField Text,"));
    assertEquals("othersField TextField Text", (new AuthorAbbreviator()).format(" and othersField TextField Text"));
    assertEquals("", (new AuthorAbbreviator()).format(", ,"));
  }
}

