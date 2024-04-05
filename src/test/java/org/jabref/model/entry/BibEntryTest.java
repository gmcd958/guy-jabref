package org.jabref.model.entry;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.jabref.model.FieldChange;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.entry.field.BibField;
import org.jabref.model.entry.field.Field;
import org.jabref.model.entry.field.FieldPriority;
import org.jabref.model.entry.field.InternalField;
import org.jabref.model.entry.field.OrFields;
import org.jabref.model.entry.field.SpecialField;
import org.jabref.model.entry.field.StandardField;
import org.jabref.model.entry.field.UnknownField;
import org.jabref.model.entry.types.StandardEntryType;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

@Execution(CONCURRENT)
class BibEntryTest {
    private BibEntry entry = new BibEntry();

    /*
     * JBR-3
     */
    @Test
    void testDefaultConstructor() {
        assertEquals(StandardEntryType.Misc, entry.getType());
        assertNotNull(entry.getId());
        assertFalse(entry.getField(StandardField.AUTHOR).isPresent());
    }

    /*
     * JBR-3
     */
    @Test
    void settingTypeToNullThrowsException() {
        assertThrows(NullPointerException.class, () -> entry.setType(null));
    }

    /*
     * JBR-3
     */
    @Test
    void setNullFieldThrowsNPE() {
        assertThrows(NullPointerException.class, () -> entry.setField(null));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldIsCaseInsensitive() throws Exception {
        entry.setField(new UnknownField("TeSt"), "value");
        assertEquals(Optional.of("value"), entry.getField(new UnknownField("tEsT")));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldWorksWithBibFieldAsWell() throws Exception {
        entry.setField(StandardField.AUTHOR, "value");
        assertEquals(Optional.of("value"), entry.getField(new BibField(StandardField.AUTHOR, FieldPriority.IMPORTANT).field()));
    }

    /*
     * JBR-3
     */
    @Test
    void newBibEntryIsUnchanged() {
        assertFalse(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void setFieldLeadsToAChangedEntry() throws Exception {
        entry.setField(StandardField.AUTHOR, "value");
        assertTrue(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void setFieldWorksWithBibFieldAsWell() throws Exception {
        entry.setField(new BibField(StandardField.AUTHOR, FieldPriority.IMPORTANT).field(), "value");
        assertEquals(Optional.of("value"), entry.getField(StandardField.AUTHOR));
    }

    /*
     * JBR-3
     */
    @Test
    void clonedBibEntryHasUniqueID() throws Exception {
        BibEntry entryClone = (BibEntry) entry.clone();
        assertNotEquals(entry.getId(), entryClone.getId());
    }

    /*
     * JBR-3
     */
    @Test
    void clonedBibEntryWithMiscTypeHasOriginalChangedFlag() throws Exception {
        BibEntry entryClone = (BibEntry) entry.clone();
        assertFalse(entryClone.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void clonedBibEntryWithBookTypeAndOneFieldHasOriginalChangedFlag() throws Exception {
        entry = new BibEntry(StandardEntryType.Book).withField(StandardField.AUTHOR, "value");
        BibEntry entryClone = (BibEntry) entry.clone();
        assertFalse(entryClone.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void setAndGetAreConsistentForMonth() throws Exception {
        entry.setField(StandardField.MONTH, "may");
        assertEquals(Optional.of("may"), entry.getField(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void setAndGetAreConsistentForCapitalizedMonth() throws Exception {
        entry.setField(StandardField.MONTH, "May");
        assertEquals(Optional.of("May"), entry.getField(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void setAndGetAreConsistentForMonthString() throws Exception {
        entry.setField(StandardField.MONTH, "#may#");
        assertEquals(Optional.of("#may#"), entry.getField(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void monthCorrectlyReturnedForMonth() throws Exception {
        entry.setField(StandardField.MONTH, "may");
        assertEquals(Optional.of(Month.MAY), entry.getMonth());
    }

    /*
     * JBR-3
     */
    @Test
    void monthCorrectlyReturnedForCapitalizedMonth() throws Exception {
        entry.setField(StandardField.MONTH, "May");
        assertEquals(Optional.of(Month.MAY), entry.getMonth());
    }

    /*
     * JBR-3
     */
    @Test
    void monthCorrectlyReturnedForMonthString() throws Exception {
        entry.setField(StandardField.MONTH, "#may#");
        assertEquals(Optional.of(Month.MAY), entry.getMonth());
    }

    /*
     * JBR-3
     */
    @Test
    void monthCorrectlyReturnedForMonthMay() throws Exception {
        entry.setMonth(Month.MAY);
        assertEquals(Optional.of(Month.MAY), entry.getMonth());
    }

    /*
     * JBR-3
     */
    @Test
    void monthFieldCorrectlyReturnedForMonthMay() throws Exception {
        entry.setMonth(Month.MAY);
        assertEquals(Optional.of("#may#"), entry.getField(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasDateWithYearNumericalMonthString() {
        entry.setField(StandardField.YEAR, "2003");
        entry.setField(StandardField.MONTH, "3");
        assertEquals(Optional.of("2003-03"), entry.getFieldOrAlias(StandardField.DATE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasDateWithYearAbbreviatedMonth() {
        entry.setField(StandardField.YEAR, "2003");
        entry.setField(StandardField.MONTH, "#mar#");
        assertEquals(Optional.of("2003-03"), entry.getFieldOrAlias(StandardField.DATE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasDateWithYearAbbreviatedMonthString() {
        entry.setField(StandardField.YEAR, "2003");
        entry.setField(StandardField.MONTH, "mar");
        assertEquals(Optional.of("2003-03"), entry.getFieldOrAlias(StandardField.DATE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasDateWithOnlyYear() {
        entry.setField(StandardField.YEAR, "2003");
        assertEquals(Optional.of("2003"), entry.getFieldOrAlias(StandardField.DATE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasYearWithDateYYYY() {
        entry.setField(StandardField.DATE, "2003");
        assertEquals(Optional.of("2003"), entry.getFieldOrAlias(StandardField.YEAR));
    }

    @Test
    void getFieldOrAliasYearWithDateYYYYMM() {
        entry.setField(StandardField.DATE, "2003-03");
        assertEquals(Optional.of("2003"), entry.getFieldOrAlias(StandardField.YEAR));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasYearWithDateYYYYMMDD() {
        entry.setField(StandardField.DATE, "2003-03-30");
        assertEquals(Optional.of("2003"), entry.getFieldOrAlias(StandardField.YEAR));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasMonthWithDateYYYYReturnsNull() {
        entry.setField(StandardField.DATE, "2003");
        assertEquals(Optional.empty(), entry.getFieldOrAlias(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasMonthWithDateYYYYMM() {
        entry.setField(StandardField.DATE, "2003-03");
        assertEquals(Optional.of("#mar#"), entry.getFieldOrAlias(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasMonthWithDateYYYYMMDD() {
        entry.setField(StandardField.DATE, "2003-03-30");
        assertEquals(Optional.of("#mar#"), entry.getFieldOrAlias(StandardField.MONTH));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasLatexFreeAlreadyFreeValueIsUnchanged() {
        entry.setField(StandardField.TITLE, "A Title Without any LaTeX commands");
        assertEquals(Optional.of("A Title Without any LaTeX commands"), entry.getFieldOrAliasLatexFree(StandardField.TITLE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasLatexFreeAlreadyFreeAliasValueIsUnchanged() {
        entry.setField(StandardField.JOURNAL, "A Title Without any LaTeX commands");
        assertEquals(Optional.of("A Title Without any LaTeX commands"), entry.getFieldOrAliasLatexFree(StandardField.JOURNALTITLE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasLatexFreeBracesAreRemoved() {
        entry.setField(StandardField.TITLE, "{A Title with some {B}ra{C}es}");
        assertEquals(Optional.of("A Title with some BraCes"), entry.getFieldOrAliasLatexFree(StandardField.TITLE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasLatexFreeBracesAreRemovedFromAlias() {
        entry.setField(StandardField.JOURNAL, "{A Title with some {B}ra{C}es}");
        assertEquals(Optional.of("A Title with some BraCes"), entry.getFieldOrAliasLatexFree(StandardField.JOURNALTITLE));
    }

    /*
     * JBR-3
     */
    @Test
    void getFieldOrAliasLatexFreeComplexConversionInAlias() {
        entry.setField(StandardField.JOURNAL, "A 32~{mA} {$\\Sigma\\Delta$}-modulator");
        assertEquals(Optional.of("A 32 mA ΣΔ-modulator"), entry.getFieldOrAliasLatexFree(StandardField.JOURNALTITLE));
    }

    /*
     * JBR-3
     */
    @Test
    void testGetAndAddToLinkedFileList() {
        List<LinkedFile> files = entry.getFiles();
        files.add(new LinkedFile("", Path.of(""), ""));
        entry.setFiles(files);
        assertEquals(Arrays.asList(new LinkedFile("", Path.of(""), "")), entry.getFiles());
    }

    /*
     * JBR-3
     */
    @Test
    void replaceOfLinkWorks() throws Exception {
        List<LinkedFile> files = new ArrayList<>();
        String urlAsString = "https://www.example.org/file.pdf";
        files.add(new LinkedFile(new URL(urlAsString), ""));
        entry.setFiles(files);

        LinkedFile linkedFile = new LinkedFile("", Path.of("file.pdf", ""), "");
        entry.replaceDownloadedFile(urlAsString, linkedFile);
        assertEquals(List.of(linkedFile), entry.getFiles());
    }

    /*
     * JBR-3
     */
    @Test
    void testGetEmptyKeywords() {
        KeywordList actual = entry.getKeywords(',');

        assertEquals(new KeywordList(), actual);
    }

    /*
     * JBR-3
     */
    @Test
    void testGetSingleKeywords() {
        entry.addKeyword("kw", ',');
        KeywordList actual = entry.getKeywords(',');

        assertEquals(new KeywordList(new Keyword("kw")), actual);
    }

    /*
     * JBR-3
     */
    @Test
    void settingCiteKeyLeadsToCorrectCiteKey() {
        assertFalse(entry.hasCitationKey());
        entry.setCitationKey("Einstein1931");
        assertEquals(Optional.of("Einstein1931"), entry.getCitationKey());
    }

    /*
     * JBR-3
     */
    @Test
    void settingCiteKeyLeadsToHasCiteKy() {
        assertFalse(entry.hasCitationKey());
        entry.setCitationKey("Einstein1931");
        assertTrue(entry.hasCitationKey());
    }

    /*
     * JBR-3
     */
    @Test
    void clearFieldWorksForAuthor() {
        entry.setField(StandardField.AUTHOR, "Albert Einstein");
        entry.clearField(StandardField.AUTHOR);
        assertEquals(Optional.empty(), entry.getField(StandardField.AUTHOR));
    }

    /*
     * JBR-3
     */
    @Test
    void setFieldWorksForAuthor() {
        entry.setField(StandardField.AUTHOR, "Albert Einstein");
        assertEquals(Optional.of("Albert Einstein"), entry.getField(StandardField.AUTHOR));
    }

    /*
     * JBR-3
     */
    @Test
    void allFieldsPresentDefault() {
        BibEntry e = new BibEntry(StandardEntryType.Article);
        e.setField(StandardField.AUTHOR, "abc");
        e.setField(StandardField.TITLE, "abc");
        e.setField(StandardField.JOURNAL, "abc");

        List<OrFields> requiredFields = new ArrayList<>();
        requiredFields.add(new OrFields(StandardField.AUTHOR));
        requiredFields.add(new OrFields(StandardField.TITLE));
        assertTrue(e.allFieldsPresent(requiredFields, null));

        requiredFields.add(new OrFields(StandardField.YEAR));
        assertFalse(e.allFieldsPresent(requiredFields, null));
    }

    /*
     * JBR-3
     */
    @Test
    void allFieldsPresentOr() {
        BibEntry e = new BibEntry(StandardEntryType.Article);
        e.setField(StandardField.AUTHOR, "abc");
        e.setField(StandardField.TITLE, "abc");
        e.setField(StandardField.JOURNAL, "abc");

        List<OrFields> requiredFields = new ArrayList<>();
        requiredFields.add(new OrFields(StandardField.JOURNAL, StandardField.YEAR));
        assertTrue(e.allFieldsPresent(requiredFields, null));

        requiredFields.add(new OrFields(StandardField.YEAR, StandardField.ADDRESS));
        assertFalse(e.allFieldsPresent(requiredFields, null));
    }

    /*
     * JBR-3
     */
    @Test
    void isNullCiteKeyThrowsNPE() {
        BibEntry e = new BibEntry(StandardEntryType.Article);
        assertThrows(NullPointerException.class, () -> e.setCitationKey(null));
    }

    /*
     * JBR-3
     */
    @Test
    void isEmptyCiteKey() {
        BibEntry e = new BibEntry(StandardEntryType.Article);
        assertFalse(e.hasCitationKey());

        e.setCitationKey("");
        assertFalse(e.hasCitationKey());

        e.setCitationKey("key");
        assertTrue(e.hasCitationKey());

        e.clearField(InternalField.KEY_FIELD);
        assertFalse(e.hasCitationKey());
    }

    /*
     * JBR-3
     */
    @Test
    void identicObjectsareEqual() throws Exception {
        BibEntry otherEntry = entry;
        assertTrue(entry.equals(otherEntry));
    }

    /*
     * JBR-3
     */
    @Test
    void compareToNullObjectIsFalse() throws Exception {
        assertFalse(entry.equals(null));
    }

    /*
     * JBR-3
     */
    @Test
    void compareToDifferentClassIsFalse() throws Exception {
        assertFalse(entry.equals(new Object()));
    }

    /*
     * JBR-3
     */
    @Test
    void compareIsTrueWhenIdAndFieldsAreEqual() throws Exception {
        entry.setId("1");
        entry.setField(new UnknownField("key"), "value");
        BibEntry otherEntry = new BibEntry();
        otherEntry.setId("1");
        assertNotEquals(entry, otherEntry);
        otherEntry.setField(new UnknownField("key"), "value");
        assertEquals(entry, otherEntry);
    }

    /*
     * JBR-3
     */
    @Test
    void addNullKeywordThrowsNPE() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        assertThrows(NullPointerException.class, () -> entry.addKeyword((Keyword) null, ','));
    }

    /*
     * JBR-3
     */
    @Test
    void putNullKeywordListThrowsNPE() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        assertThrows(NullPointerException.class, () -> entry.putKeywords((KeywordList) null, ','));
    }

    /*
     * JBR-3
     */
    @Test
    void putNullKeywordSeparatorThrowsNPE() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        assertThrows(NullPointerException.class, () -> entry.putKeywords(Arrays.asList("A", "B"), null));
    }

    /*
     * JBR-3
     */
    @Test
    void testGetSeparatedKeywordsAreCorrect() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        assertEquals(new KeywordList("Foo", "Bar"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordIsCorrect() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.addKeyword("FooBar", ',');
        assertEquals(new KeywordList("Foo", "Bar", "FooBar"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordHasChanged() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.addKeyword("FooBar", ',');
        assertTrue(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordTwiceYiedsOnlyOne() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.addKeyword("FooBar", ',');
        entry.addKeyword("FooBar", ',');
        assertEquals(new KeywordList("Foo", "Bar", "FooBar"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void addKeywordIsCaseSensitive() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.addKeyword("FOO", ',');
        assertEquals(new KeywordList("Foo", "Bar", "FOO"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordWithDifferentCapitalizationChanges() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.addKeyword("FOO", ',');
        assertTrue(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordEmptyKeywordIsNotAdded() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.addKeyword("", ',');
        assertEquals(new KeywordList("Foo", "Bar"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordEmptyKeywordNotChanged() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.setChanged(false);
        entry.addKeyword("", ',');
        assertFalse(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void texNewBibEntryHasNoKeywords() {
        assertTrue(entry.getKeywords(',').isEmpty());
    }

    /*
     * JBR-3
     */
    @Test
    void texNewBibEntryHasNoKeywordsEvenAfterAddingEmptyKeyword() {
        entry.addKeyword("", ',');
        assertTrue(entry.getKeywords(',').isEmpty());
    }

    /*
     * JBR-3
     */
    @Test
    void texNewBibEntryAfterAddingEmptyKeywordNotChanged() {
        entry.addKeyword("", ',');
        assertFalse(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void testAddKeywordsWorksAsExpected() {
        entry.addKeywords(Arrays.asList("Foo", "Bar"), ',');
        assertEquals(new KeywordList("Foo", "Bar"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void testPutKeywordsOverwritesOldKeywords() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Arrays.asList("Yin", "Yang"), ',');
        assertEquals(new KeywordList("Yin", "Yang"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void testPutKeywordsHasChanged() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Arrays.asList("Yin", "Yang"), ',');
        assertTrue(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void testPutKeywordsPutEmpyListErasesPreviousKeywords() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Collections.emptyList(), ',');
        assertTrue(entry.getKeywords(',').isEmpty());
    }

    /*
     * JBR-3
     */
    @Test
    void testPutKeywordsPutEmpyListHasChanged() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Collections.emptyList(), ',');
        assertTrue(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void testPutKeywordsPutEmpyListToEmptyBibentry() {
        entry.putKeywords(Collections.emptyList(), ',');
        assertTrue(entry.getKeywords(',').isEmpty());
    }

    /*
     * JBR-3
     */
    @Test
    void testPutKeywordsPutEmpyListToEmptyBibentryNotChanged() {
        entry.putKeywords(Collections.emptyList(), ',');
        assertFalse(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void putKeywordsToEmptyReturnsNoChange() {
        Optional<FieldChange> change = entry.putKeywords(Collections.emptyList(), ',');
        assertEquals(Optional.empty(), change);
    }

    /*
     * JBR-3
     */
    @Test
    void clearKeywordsReturnsChange() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        Optional<FieldChange> change = entry.putKeywords(Collections.emptyList(), ',');
        assertEquals(Optional.of(new FieldChange(entry, StandardField.KEYWORDS, "Foo, Bar", null)), change);
    }

    /*
     * JBR-3
     */
    @Test
    void changeKeywordsReturnsChange() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        Optional<FieldChange> change = entry.putKeywords(Arrays.asList("Test", "FooTest"), ',');
        assertEquals(Optional.of(new FieldChange(entry, StandardField.KEYWORDS, "Foo, Bar", "Test, FooTest")),
                change);
    }

    /*
     * JBR-3
     */
    @Test
    void putKeywordsToSameReturnsNoChange() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        Optional<FieldChange> change = entry.putKeywords(Arrays.asList("Foo", "Bar"), ',');
        assertEquals(Optional.empty(), change);
    }

    /*
     * JBR-3
     */
    @Test
    void getKeywordsReturnsParsedKeywordListFromKeywordsField() {
        entry.setField(StandardField.KEYWORDS, "w1, w2a w2b, w3");
        assertEquals(new KeywordList("w1", "w2a w2b", "w3"), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void removeKeywordsOnEntryWithoutKeywordsDoesNothing() {
        Optional<FieldChange> change = entry.removeKeywords(SpecialField.RANKING.getKeyWords(), ',');
        assertEquals(Optional.empty(), change);
    }

    /*
     * JBR-3
     */
    @Test
    void removeKeywordsWithEmptyListDoesNothing() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Arrays.asList("kw1", "kw2"), ',');
        Optional<FieldChange> change = entry.removeKeywords(new KeywordList(), ',');
        assertEquals(Optional.empty(), change);
    }

    /*
     * JBR-3
     */
    @Test
    void removeKeywordsWithNonExistingKeywordsDoesNothing() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Arrays.asList("kw1", "kw2"), ',');
        Optional<FieldChange> change = entry.removeKeywords(KeywordList.parse("kw3, kw4", ','), ',');
        assertEquals(Optional.empty(), change);
        assertEquals(Sets.newHashSet("kw1", "kw2"), entry.getKeywords(',').toStringList());
    }

    /*
     * JBR-3
     */
    @Test
    void removeKeywordsWithExistingKeywordsRemovesThem() {
        entry.setField(StandardField.KEYWORDS, "Foo, Bar");
        entry.putKeywords(Arrays.asList("kw1", "kw2", "kw3"), ',');
        Optional<FieldChange> change = entry.removeKeywords(KeywordList.parse("kw1, kw2", ','), ',');
        assertTrue(change.isPresent());
        assertEquals(KeywordList.parse("kw3", ','), entry.getKeywords(','));
    }

    /*
     * JBR-3
     */
    @Test
    void keywordListCorrectlyConstructedForThreeKeywords() {
        entry.addKeyword("kw", ',');
        entry.addKeyword("kw2", ',');
        entry.addKeyword("kw3", ',');
        KeywordList actual = entry.getKeywords(',');
        assertEquals(new KeywordList(new Keyword("kw"), new Keyword("kw2"), new Keyword("kw3")), actual);
    }

    /*
     * JBR-3
     */
    @Test
    void testGetEmptyResolvedKeywords() {
        BibDatabase database = new BibDatabase();
        entry.setField(StandardField.CROSSREF, "entry2");
        database.insertEntry(entry);

        BibEntry entry2 = new BibEntry();
        entry2.setCitationKey("entry2");
        database.insertEntry(entry2);

        KeywordList actual = entry.getResolvedKeywords(',', database);

        assertEquals(new KeywordList(), actual);
    }

    /*
     * JBR-3
     */
    @Test
    void testGetSingleResolvedKeywords() {
        BibDatabase database = new BibDatabase();
        entry.setField(StandardField.CROSSREF, "entry2");

        BibEntry entry2 = new BibEntry();
        entry2.setCitationKey("entry2");
        entry2.addKeyword("kw", ',');

        database.insertEntry(entry2);
        database.insertEntry(entry);

        KeywordList actual = entry.getResolvedKeywords(',', database);

        assertEquals(new KeywordList(new Keyword("kw")), actual);
    }

    /*
     * JBR-3
     */
    @Test
    void testGetResolvedKeywords() {
        BibDatabase database = new BibDatabase();
        entry.setField(StandardField.CROSSREF, "entry2");

        BibEntry entry2 = new BibEntry();
        entry2.setCitationKey("entry2");
        entry2.addKeyword("kw", ',');
        entry2.addKeyword("kw2", ',');
        entry2.addKeyword("kw3", ',');

        database.insertEntry(entry2);
        database.insertEntry(entry);

        KeywordList actual = entry.getResolvedKeywords(',', database);

        assertEquals(new KeywordList(new Keyword("kw"), new Keyword("kw2"), new Keyword("kw3")), actual);
    }

    /*
     * JBR-3
     */
    @Test
    void settingTitleFieldsLeadsToChangeFlagged() {
        entry.setField(StandardField.AUTHOR, "value");
        assertTrue(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void builderReturnsABibEntryNotChangedFlagged() {
        entry = new BibEntry().withField(StandardField.AUTHOR, "value");
        assertFalse(entry.hasChanged());
    }

    /*
     * JBR-3
     */
    @Test
    void mergeEntriesWithNoOverlap() {
        BibEntry expected = new BibEntry()
                .withField(StandardField.AUTHOR, "Test Author")
                .withField(StandardField.TITLE, "Test Title")
                .withField(StandardField.EPRINT, "1234.56789")
                .withField(StandardField.DATE, "1970-01-01");

        BibEntry copyEntry = (BibEntry) entry.clone();
        BibEntry otherEntry = new BibEntry();

        copyEntry.setField(Map.of(
                StandardField.AUTHOR, "Test Author",
                StandardField.TITLE, "Test Title"));

        otherEntry.setField(Map.of(
                StandardField.EPRINT, "1234.56789",
                StandardField.DATE, "1970-01-01"
        ));

        copyEntry.mergeWith(otherEntry);
        assertEquals(expected.getFields(), copyEntry.getFields());
    }

    /*
     * JBR-3
     */
    @Test
    void mergeEntriesWithOverlap() {
        BibEntry expected = new BibEntry()
                .withField(StandardField.AUTHOR, "Test Author")
                .withField(StandardField.TITLE, "Test Title")
                .withField(StandardField.DATE, "1970-01-01");

        BibEntry copyEntry = (BibEntry) entry.clone();
        BibEntry otherEntry = new BibEntry();

        copyEntry.setField(Map.of(
                StandardField.AUTHOR, "Test Author",
                StandardField.TITLE, "Test Title"));

        otherEntry.setField(Map.of(
                StandardField.AUTHOR, "Another Test Author",
                StandardField.DATE, "1970-01-01"
        ));

        copyEntry.mergeWith(otherEntry);
        assertEquals(expected.getFields(), copyEntry.getFields());
    }

    /*
     * JBR-3
     */
    @Test
    void mergeEntriesWithNoOverlapAndNonExistingPriorityFields() {
        BibEntry expected = new BibEntry()
                .withField(StandardField.AUTHOR, "Test Author")
                .withField(StandardField.TITLE, "Test Title")
                .withField(StandardField.EPRINT, "1234.56789")
                .withField(StandardField.DATE, "1970-01-01");

        BibEntry copyEntry = (BibEntry) entry.clone();
        BibEntry otherEntry = new BibEntry();

        copyEntry.setField(Map.of(
                StandardField.AUTHOR, "Test Author",
                StandardField.TITLE, "Test Title"));

        otherEntry.setField(Map.of(
                StandardField.EPRINT, "1234.56789",
                StandardField.DATE, "1970-01-01"
        ));

        Set<Field> otherPrioritizedFields = Set.of(StandardField.VOLUME, StandardField.KEYWORDS);

        copyEntry.mergeWith(otherEntry, otherPrioritizedFields);
        assertEquals(expected.getFields(), copyEntry.getFields());
    }

    /*
     * JBR-3
     */
    @Test
    void mergeEntriesWithNoOverlapAndExistingPriorityFields() {
        BibEntry expected = new BibEntry()
                .withField(StandardField.AUTHOR, "Test Author")
                .withField(StandardField.TITLE, "Test Title")
                .withField(StandardField.EPRINT, "1234.56789")
                .withField(StandardField.DATE, "1970-01-01");

        BibEntry copyEntry = (BibEntry) entry.clone();
        BibEntry otherEntry = new BibEntry();

        copyEntry.setField(Map.of(
                StandardField.AUTHOR, "Test Author",
                StandardField.TITLE, "Test Title"));

        otherEntry.setField(Map.of(
                StandardField.EPRINT, "1234.56789",
                StandardField.DATE, "1970-01-01"
        ));

        Set<Field> otherPrioritizedFields = Set.of(StandardField.AUTHOR, StandardField.EPRINT);

        copyEntry.mergeWith(otherEntry, otherPrioritizedFields);
        assertEquals(expected.getFields(), copyEntry.getFields());
    }

    /*
     * JBR-3
     */
    @Test
    void mergeEntriesWithOverlapAndPriorityGivenToNonOverlappingField() {
        BibEntry expected = new BibEntry()
                .withField(StandardField.AUTHOR, "Test Author")
                .withField(StandardField.TITLE, "Test Title")
                .withField(StandardField.DATE, "1970-01-01");

        BibEntry copyEntry = (BibEntry) entry.clone();
        BibEntry otherEntry = new BibEntry();

        copyEntry.setField(Map.of(
                StandardField.AUTHOR, "Test Author",
                StandardField.TITLE, "Test Title"));

        otherEntry.setField(Map.of(
                StandardField.AUTHOR, "Another Test Author",
                StandardField.DATE, "1970-01-01"
        ));

        Set<Field> otherPrioritizedFields = Set.of(StandardField.TITLE, StandardField.DATE);

        copyEntry.mergeWith(otherEntry, otherPrioritizedFields);
        assertEquals(expected.getFields(), copyEntry.getFields());
    }

    /*
     * JBR-3
     */
    @Test
    void mergeEntriesWithOverlapAndPriorityGivenToOverlappingField() {
        BibEntry expected = new BibEntry()
                .withField(StandardField.AUTHOR, "Another Test Author")
                .withField(StandardField.TITLE, "Test Title")
                .withField(StandardField.DATE, "1970-01-01");

        BibEntry copyEntry = (BibEntry) entry.clone();
        BibEntry otherEntry = new BibEntry();

        copyEntry.setField(Map.of(
                StandardField.AUTHOR, "Test Author",
                StandardField.TITLE, "Test Title"));

        otherEntry.setField(Map.of(
                StandardField.AUTHOR, "Another Test Author",
                StandardField.DATE, "1970-01-01"
        ));

        Set<Field> otherPrioritizedFields = Set.of(StandardField.AUTHOR, StandardField.DATE);

        copyEntry.mergeWith(otherEntry, otherPrioritizedFields);
        assertEquals(expected.getFields(), copyEntry.getFields());
    }

    /*
     * JBR-3
     */
    public static Stream<BibEntry> isEmpty() {
        return Stream.of(
                new BibEntry(),
                new BibEntry(StandardEntryType.Book),
                new BibEntry().withField(StandardField.OWNER, "test"),
                new BibEntry().withField(StandardField.CREATIONDATE, "test"),
                new BibEntry()
                        .withField(StandardField.OWNER, "test")
                        .withField(StandardField.CREATIONDATE, "test"),
                // source: https://github.com/JabRef/jabref/issues/8645
                new BibEntry()
                        .withField(StandardField.OWNER, "mlep")
                        .withField(StandardField.CREATIONDATE, "2022-04-05T10:41:54"));
    }

    @ParameterizedTest
    @MethodSource
    void isEmpty(BibEntry entry) {
        assertTrue(entry.isEmpty());
    }

    public static Stream<BibEntry> isNotEmpty() {
        return Stream.of(
                new BibEntry().withCitationKey("test"),
                new BibEntry().withField(StandardField.AUTHOR, "test")
        );
    }

    @ParameterizedTest
    @MethodSource
    void isNotEmpty(BibEntry entry) {
        assertFalse(entry.isEmpty());
    }
}
