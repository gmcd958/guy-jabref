package org.jabref.logic.journals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.DuplicateHeaderMode;
import org.junit.jupiter.api.Test;

class AbbreviationFormatDiffblueTest {
  /**
  * Method under test: {@link AbbreviationFormat#getCSVFormat()}
  */
  @Test
  void testGetCSVFormat() {
    // Arrange and Act
    CSVFormat actualCSVFormat = AbbreviationFormat.getCSVFormat();

    // Assert
    assertTrue(actualCSVFormat.isQuoteCharacterSet());
    assertFalse(actualCSVFormat.getAllowMissingColumnNames());
    assertFalse(actualCSVFormat.isNullStringSet());
    assertTrue(actualCSVFormat.isEscapeCharacterSet());
    assertFalse(actualCSVFormat.isCommentMarkerSet());
    assertTrue(actualCSVFormat.getTrim());
    assertFalse(actualCSVFormat.getTrailingDelimiter());
    assertFalse(actualCSVFormat.getSkipHeaderRecord());
    assertEquals("\r\n", actualCSVFormat.getRecordSeparator());
    assertFalse(actualCSVFormat.getIgnoreSurroundingSpaces());
    assertFalse(actualCSVFormat.getIgnoreHeaderCase());
    assertTrue(actualCSVFormat.getIgnoreEmptyLines());
    assertNull(actualCSVFormat.getHeaderComments());
    assertNull(actualCSVFormat.getHeader());
    assertEquals(DuplicateHeaderMode.ALLOW_ALL, actualCSVFormat.getDuplicateHeaderMode());
    assertEquals(";", actualCSVFormat.getDelimiterString());
    assertNull(actualCSVFormat.getQuoteMode());
    assertFalse(actualCSVFormat.getAutoFlush());
  }
}

