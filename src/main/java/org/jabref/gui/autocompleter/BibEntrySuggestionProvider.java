package org.jabref.gui.autocompleter;

import java.util.Comparator;
import java.util.stream.Stream;

import org.jabref.logic.bibtex.comparator.EntryComparator;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.InternalField;
import org.jabref.model.strings.StringUtil;

import com.google.common.base.Equivalence;
import org.controlsfx.control.textfield.AutoCompletionBinding;

/**
 * Delivers possible completions as a list of {@link BibEntry} based on their citation key.
 */
public class BibEntrySuggestionProvider extends SuggestionProvider<BibEntry> {

    private final BibDatabase database;

    /*
     * JBR-4
     */
    public BibEntrySuggestionProvider(BibDatabase database) {
        this.database = database;
    }

    /*
     * JBR-4
     */
    @Override
    protected Equivalence<BibEntry> getEquivalence() {
        // TODO: add proper logging
        System.out.println("Got Equivalence!");
        System.out.println("Test");
        return Equivalence.equals().onResultOf(BibEntry::getCitationKey);
    }

    /*
     * JBR-4
     */
    @Override
    protected Comparator<BibEntry> getComparator() {
        return new EntryComparator(false, true, InternalField.KEY_FIELD);
    }

    /*
     * JBR-4
     */
    @Override
    protected boolean isMatch(BibEntry entry, AutoCompletionBinding.ISuggestionRequest request) {
        String userText = request.getUserText();
        return entry.getCitationKey()
                    .map(key -> StringUtil.containsIgnoreCase(key, userText))
                    .orElse(false);
    }

    /*
     * JBR-4
     */
    @Override
    public Stream<BibEntry> getSource() {
        return database.getEntries().parallelStream();
    }
}
