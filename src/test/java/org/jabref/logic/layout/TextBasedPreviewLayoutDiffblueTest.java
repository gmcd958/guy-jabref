package org.jabref.logic.layout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import org.jabref.gui.util.uithreadaware.UiThreadStringProperty;
import org.jabref.logic.layout.format.NameFormatterPreferences;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TextBasedPreviewLayoutDiffblueTest {
  /**
  * Method under test: {@link TextBasedPreviewLayout#generatePreview(BibEntry, BibDatabaseContext)}
  */
  @Test
  void testGeneratePreview() {
    // Arrange
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt");
    ReadOnlyStringWrapper delegate = mock(ReadOnlyStringWrapper.class);
    doNothing().when(delegate).addListener(Mockito.<ChangeListener<String>>any());
    doNothing().when(delegate).addListener(Mockito.<InvalidationListener>any());
    delegate.addListener(mock(InvalidationListener.class));

    UiThreadStringProperty mainFileDirectoryProperty = new UiThreadStringProperty(delegate);
    mainFileDirectoryProperty.addListener(mock(ChangeListener.class));
    LayoutFormatterPreferences layoutPreferences = new LayoutFormatterPreferences(mock(NameFormatterPreferences.class),
        mainFileDirectoryProperty);

    ArrayList<StringInt> parsedEntries = new ArrayList<>();
    TextBasedPreviewLayout textBasedPreviewLayout = new TextBasedPreviewLayout(
        new Layout(parsedEntries, new ArrayList<>(), layoutPreferences, null));
    BibEntry entry = new BibEntry();

    // Act and Assert
    assertEquals("", textBasedPreviewLayout.generatePreview(entry, new BibDatabaseContext()));
    verify(delegate).addListener(Mockito.<InvalidationListener>any());
    verify(delegate).addListener(Mockito.<ChangeListener<String>>any());
  }

  /**
   * Method under test: {@link TextBasedPreviewLayout#generatePreview(BibEntry, BibDatabaseContext)}
   */
  @Test
  void testGeneratePreview2() {
    // Arrange
    Paths.get(System.getProperty("java.io.tmpdir"), "test.txt");

    ArrayList<StringInt> parsedEntries = new ArrayList<>();
    parsedEntries.add(new StringInt("foo", 1));
    ReadOnlyStringWrapper delegate = mock(ReadOnlyStringWrapper.class);
    doNothing().when(delegate).addListener(Mockito.<ChangeListener<String>>any());
    doNothing().when(delegate).addListener(Mockito.<InvalidationListener>any());
    delegate.addListener(mock(InvalidationListener.class));

    UiThreadStringProperty mainFileDirectoryProperty = new UiThreadStringProperty(delegate);
    mainFileDirectoryProperty.addListener(mock(ChangeListener.class));
    LayoutFormatterPreferences layoutPreferences = new LayoutFormatterPreferences(mock(NameFormatterPreferences.class),
        mainFileDirectoryProperty);

    TextBasedPreviewLayout textBasedPreviewLayout = new TextBasedPreviewLayout(
        new Layout(parsedEntries, new ArrayList<>(), layoutPreferences, null));
    BibEntry entry = new BibEntry();

    // Act and Assert
    assertEquals("foo", textBasedPreviewLayout.generatePreview(entry, new BibDatabaseContext()));
    verify(delegate).addListener(Mockito.<InvalidationListener>any());
    verify(delegate).addListener(Mockito.<ChangeListener<String>>any());
  }
}

