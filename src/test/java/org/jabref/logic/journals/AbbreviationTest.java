package org.jabref.logic.journals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AbbreviationTest {

    /*
     * JBR-2
     */
    @Test
    void testAbbreviationsWithTrailingSpaces() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.");

        assertEquals("Long Name", abbreviation.getName());
        assertEquals("L. N.", abbreviation.getAbbreviation());
        assertEquals("L N", abbreviation.getDotlessAbbreviation());
        assertEquals("L. N.", abbreviation.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testAbbreviationsWithTrailingSpacesWithShortestUniqueAbbreviation() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.", "LN");

        assertEquals("Long Name", abbreviation.getName());
        assertEquals("L. N.", abbreviation.getAbbreviation());
        assertEquals("L N", abbreviation.getDotlessAbbreviation());
        assertEquals("LN", abbreviation.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testAbbreviationsWithSemicolons() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.;LN;M");

        assertEquals("Long Name", abbreviation.getName());
        assertEquals("L. N.;LN;M", abbreviation.getAbbreviation());
        assertEquals("L N ;LN;M", abbreviation.getDotlessAbbreviation());
        assertEquals("L. N.;LN;M", abbreviation.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testAbbreviationsWithSemicolonsWithShortestUniqueAbbreviation() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.;LN;M", "LNLNM");

        assertEquals("Long Name", abbreviation.getName());
        assertEquals("L. N.;LN;M", abbreviation.getAbbreviation());
        assertEquals("L N ;LN;M", abbreviation.getDotlessAbbreviation());
        assertEquals("LNLNM", abbreviation.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testGetNextElement() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.");

        assertEquals("L. N.", abbreviation.getNext("Long Name"));
        assertEquals("L N", abbreviation.getNext("L. N."));
        assertEquals("Long Name", abbreviation.getNext("L N"));
    }

    /*
     * JBR-2
     */
    @Test
    void testGetNextElementWithShortestUniqueAbbreviation() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.", "LN");

        assertEquals("L. N.", abbreviation.getNext("Long Name"));
        assertEquals("L N", abbreviation.getNext("L. N."));
        assertEquals("LN", abbreviation.getNext("L N"));
        assertEquals("Long Name", abbreviation.getNext("LN"));
    }

    /*
     * JBR-2
     */
    @Test
    void testGetNextElementWithTrailingSpaces() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.");

        assertEquals("L. N.", abbreviation.getNext("Long Name"));
        assertEquals("L N", abbreviation.getNext("L. N."));
        assertEquals("Long Name", abbreviation.getNext("L N"));
    }

    /*
     * JBR-2
     */
    @Test
    void testGetNextElementWithTrailingSpacesWithShortestUniqueAbbreviation() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L. N.", "LN");

        assertEquals("L. N.", abbreviation.getNext("Long Name"));
        assertEquals("L N", abbreviation.getNext("L. N."));
        assertEquals("LN", abbreviation.getNext("L N"));
        assertEquals("Long Name", abbreviation.getNext("LN"));
    }

    /*
     * JBR-2
     */
    @Test
    void testDefaultAndMedlineAbbreviationsAreSame() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L N");
        assertEquals(abbreviation.getAbbreviation(), abbreviation.getDotlessAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testDefaultAndMedlineAbbreviationsAreSameWithShortestUniqueAbbreviation() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L N", "LN");
        assertEquals(abbreviation.getAbbreviation(), abbreviation.getDotlessAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testDefaultAndShortestUniqueAbbreviationsAreSame() {
        Abbreviation abbreviation = new Abbreviation("Long Name", "L N");
        assertEquals(abbreviation.getAbbreviation(), abbreviation.getShortestUniqueAbbreviation());
    }

    /*
     * JBR-2
     */
    @Test
    void testEquals() {
      Abbreviation abbreviation = new Abbreviation("Long Name", "L N", "LN");
      Abbreviation otherAbbreviation = new Abbreviation("Long Name", "L N", "LN");
      assertEquals(abbreviation, otherAbbreviation);
      assertNotEquals(abbreviation, "String");
    }

    /*
     * JBR-2
     */
    @Test
    void equalAbbrevationsWithFourComponentsAreAlsoCompareZero() {
        Abbreviation abbreviation1 = new Abbreviation("Long Name", "L. N.", "LN");
        Abbreviation abbreviation2 = new Abbreviation("Long Name", "L. N.", "LN");
        assertEquals(0, abbreviation1.compareTo(abbreviation2));
    }
}
