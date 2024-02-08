package org.jabref.logic.journals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class AbbreviationDiffblueTest {
  /**
   * Method under test: {@link Abbreviation#getShortestUniqueAbbreviation()}
   */
  @Test
  void testGetShortestUniqueAbbreviation() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Abbreviation");

    // Act and Assert
    assertEquals("Abbreviation", abbreviation.getShortestUniqueAbbreviation());
    assertTrue(abbreviation.isDefaultShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#getShortestUniqueAbbreviation()}
   */
  @Test
  void testGetShortestUniqueAbbreviation2() {
    // Arrange, Act and Assert
    assertEquals("Shortest Unique Abbreviation",
        (new Abbreviation("Name", "Abbreviation", "Shortest Unique Abbreviation")).getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#isDefaultShortestUniqueAbbreviation()}
   */
  @Test
  void testIsDefaultShortestUniqueAbbreviation() {
    // Arrange, Act and Assert
    assertTrue((new Abbreviation("Name", "Abbreviation")).isDefaultShortestUniqueAbbreviation());
    assertFalse((new Abbreviation("Name", "Abbreviation", "Shortest Unique Abbreviation"))
        .isDefaultShortestUniqueAbbreviation());
    assertTrue((new Abbreviation("Name", "Shortest Unique Abbreviation", "Shortest Unique Abbreviation"))
        .isDefaultShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#compareTo(Abbreviation)}
   */
  @Test
  void testCompareTo() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Abbreviation");
    Abbreviation toCompare = new Abbreviation("Name", "Abbreviation");

    // Act and Assert
    assertEquals(0, abbreviation.compareTo(toCompare));
    assertEquals("Abbreviation", toCompare.getShortestUniqueAbbreviation());
    assertEquals("Abbreviation", abbreviation.getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#compareTo(Abbreviation)}
   */
  @Test
  void testCompareTo2() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("", "Abbreviation");

    // Act and Assert
    assertEquals(-4, abbreviation.compareTo(new Abbreviation("Name", "Abbreviation")));
  }

  /**
   * Method under test: {@link Abbreviation#compareTo(Abbreviation)}
   */
  @Test
  void testCompareTo3() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "");

    // Act and Assert
    assertEquals(-12, abbreviation.compareTo(new Abbreviation("Name", "Abbreviation")));
  }

  /**
   * Method under test: {@link Abbreviation#compareTo(Abbreviation)}
   */
  @Test
  void testCompareTo4() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Abbreviation", "Shortest Unique Abbreviation");
    Abbreviation toCompare = new Abbreviation("Name", "Abbreviation");

    // Act and Assert
    assertEquals(18, abbreviation.compareTo(toCompare));
    assertEquals("Abbreviation", toCompare.getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Abbreviation");

    // Act and Assert
    assertEquals("Abbreviation", abbreviation.getNext("Current"));
    assertEquals("Abbreviation", abbreviation.getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext2() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Current", "Abbreviation");

    // Act and Assert
    assertEquals("Abbreviation", abbreviation.getNext("Current"));
    assertEquals("Abbreviation", abbreviation.getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext3() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Current");

    // Act and Assert
    assertEquals("Name", abbreviation.getNext("Current"));
    assertEquals("Current", abbreviation.getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext4() {
    // Arrange, Act and Assert
    assertEquals("Abbreviation", (new Abbreviation("Abbreviation", "Abbreviation", "Abbreviation")).getNext("Current"));
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext5() {
    // Arrange, Act and Assert
    assertEquals("Abbreviation", (new Abbreviation("Abbreviation", "Current", "Abbreviation")).getNext("Current"));
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext6() {
    // Arrange, Act and Assert
    assertEquals("Abbreviation", (new Abbreviation("Abbreviation", "Abbreviation", "Current")).getNext("Current"));
  }

  /**
   * Method under test: {@link Abbreviation#getNext(String)}
   */
  @Test
  void testGetNext7() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "org.jabref.logic.journals.Abbreviation");

    // Act and Assert
    assertEquals("org jabref logic journals Abbreviation",
        abbreviation.getNext("org.jabref.logic.journals.Abbreviation"));
    assertEquals("org.jabref.logic.journals.Abbreviation", abbreviation.getShortestUniqueAbbreviation());
  }

  /**
   * Method under test: {@link Abbreviation#equals(Object)}
   */
  @Test
  void testEquals() {
    // Arrange, Act and Assert
    assertNotEquals(new Abbreviation("Name", "Abbreviation"), null);
    assertNotEquals(new Abbreviation("Name", "Abbreviation"), "Different type to Abbreviation");
  }

  /**
   * Methods under test: 
   * 
   * <ul>
   *   <li>{@link Abbreviation#equals(Object)}
   *   <li>{@link Abbreviation#hashCode()}
   * </ul>
   */
  @Test
  void testEquals2() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Abbreviation");

    // Act and Assert
    assertEquals(abbreviation, abbreviation);
    int expectedHashCodeResult = abbreviation.hashCode();
    assertEquals(expectedHashCodeResult, abbreviation.hashCode());
  }

  /**
   * Methods under test: 
   * 
   * <ul>
   *   <li>{@link Abbreviation#equals(Object)}
   *   <li>{@link Abbreviation#hashCode()}
   * </ul>
   */
  @Test
  void testEquals3() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Abbreviation");
    Abbreviation abbreviation2 = new Abbreviation("Name", "Abbreviation");

    // Act and Assert
    assertEquals(abbreviation, abbreviation2);
    int expectedHashCodeResult = abbreviation.hashCode();
    assertEquals(expectedHashCodeResult, abbreviation2.hashCode());
  }

  /**
   * Method under test: {@link Abbreviation#equals(Object)}
   */
  @Test
  void testEquals4() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Abbreviation", "Abbreviation");

    // Act and Assert
    assertNotEquals(abbreviation, new Abbreviation("Name", "Abbreviation"));
  }

  /**
   * Method under test: {@link Abbreviation#equals(Object)}
   */
  @Test
  void testEquals5() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Name", "Name");

    // Act and Assert
    assertNotEquals(abbreviation, new Abbreviation("Name", "Abbreviation"));
  }

  /**
   * Methods under test: 
   * 
   * <ul>
   *   <li>{@link Abbreviation#equals(Object)}
   *   <li>{@link Abbreviation#hashCode()}
   * </ul>
   */
  @Test
  void testEquals6() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Abbreviation", "Abbreviation");
    Abbreviation abbreviation2 = new Abbreviation("Abbreviation", "Abbreviation", "Abbreviation");

    // Act and Assert
    assertEquals(abbreviation, abbreviation2);
    int expectedHashCodeResult = abbreviation.hashCode();
    assertEquals(expectedHashCodeResult, abbreviation2.hashCode());
  }

  /**
   * Method under test: {@link Abbreviation#equals(Object)}
   */
  @Test
  void testEquals7() {
    // Arrange
    Abbreviation abbreviation = new Abbreviation("Abbreviation", "Abbreviation");

    // Act and Assert
    assertNotEquals(abbreviation, new Abbreviation("Abbreviation", "Abbreviation", "Shortest Unique Abbreviation"));
  }

  /**
  * Method under test: {@link Abbreviation#toString()}
  */
  @Test
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("Abbreviation{name=Name, abbreviation=Abbreviation, dotlessAbbreviation=Abbreviation, shortestUniqueA"
        + "bbreviation=}", (new Abbreviation("Name", "Abbreviation")).toString());
  }
}

