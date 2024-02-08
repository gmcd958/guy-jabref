# Terminal Commands

./gradlew clean tiaTests teamscaleReportUpload --continue

./gradlew clean tiaTests --tests org.jabref.gui.autocompleter.BibEntrySuggestionProviderTest teamscaleReportUpload --continue

./gradlew clean tiaTests --tests org.jabref.logic.layout.TextBasedPreviewLayoutDiffblueTest  teamscaleReportUpload --continue

./gradlew clean tiaTests --tests="org.jabref.logic.layout.format.AuthorAndToSemicolonReplacerDiffblueTest,org.jabref.logic.layout.format.AuthorAndsCommaReplacerDiffblueTest,org.jabref.logic.layout.format.AuthorAbbreviatorDiffblueTest,org.jabref.logic.layout.TextBasedPreviewLayoutDiffblueTest"  teamscaleReportUpload --continue

./gradlew :clean :tiaTests --tests="org.jabref.logic.layout.format.*DiffblueTest" :teamscaleReportUpload --continue

./gradlew clean tiaTests --tests="org.jabref.logic.layout.format.AuthorAndToSemicolonReplacerDiffblueTest,org.jabref.logic.layout.format.AuthorAndsCommaReplacerDiffblueTest,org.jabref.logic.layout.format.AuthorAbbreviatorDiffblueTest,org.jabref.logic.layout.TextBasedPreviewLayoutDiffblueTest"  teamscaleReportUpload --continue --info

./gradlew clean tiaTests --tests="org.jabref.logic.layout.format.AuthorAndToSemicolonReplacerDiffblueTest.*,org.jabref.logic.layout.format.AuthorAndsCommaReplacerDiffblueTest.*,org.jabref.logic.layout.format.AuthorAbbreviatorDiffblueTest.*,org.jabref.logic.layout.TextBasedPreviewLayoutDiffblueTest.*"  teamscaleReportUpload --continue

./gradlew clean test --tests="org.jabref.logic.layout.format.AuthorAndToSemicolonReplacerDiffblueTest,org.jabref.logic.layout.format.AuthorAndsCommaReplacerDiffblueTest,org.jabref.logic.layout.format.AuthorAbbreviatorDiffblueTest,org.jabref.logic.layout.TextBasedPreviewLayoutDiffblueTest"  teamscaleReportUpload --continue

./gradlew clean tiaTests --tests="AuthorAndToSemicolonReplacerDiffblueTest,AuthorAndsCommaReplacerDiffblueTest,AuthorAbbreviatorDiffblueTest,TextBasedPreviewLayoutDiffblueTest"  teamscaleReportUpload --continue

./gradlew clean tiaTests --tests org.jabref.logic.layout.format.AuthorAndToSemicolonReplacerDiffblueTest  teamscaleReportUpload --continue

./gradlew :test --tests "org.jabref.logic.layout.**DiffblueTest"

dcover.bat create org.jabref.logic.citationstyle.CitationStyleGenerator --working-directory="C:\Users\guy.mcdonald\Documents\teamscale-diffblue\guy-jabref" --classpath="C:\Users\guy.mcdonald\Documents\teamscale-diffblue\guy-jabref\build\classes" --test-output-dir=build/classes/java/test/org/jabref/diffblue

dcover.bat create org.jabref.cli.ArgumentProcessor.automaticallySetFileLinks --working-directory="C:\Users\guy.mcdonald\Documents\teamscale-diffblue\guy-jabref" --test-output-dir=build/classes/java/test/org/jabref/diffblue --skip-test-validation

dcover.bat create org.jabref.cli.ArgumentProcessor.hasParserResults --working-directory="C:\Users\guy.mcdonald\Documents\teamscale-diffblue\guy-jabref" --test-output-dir=build/classes/java/test/org/jabref/diffblue --skip-test-validation

dcover.bat create org.jabref.cli.ArgumentProcessor --working-directory="C:\Users\guy.mcdonald\Documents\teamscale-diffblue\guy-jabref" --test-output-dir=build/classes/java/test/org/jabref/diffblue --skip-test-validation

dcover.bat activate H35R-LD2Z-VLAJ-YWCO
