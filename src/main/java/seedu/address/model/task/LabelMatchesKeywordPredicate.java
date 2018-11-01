package seedu.address.model.task;

import java.util.function.Predicate;

import seedu.address.model.tag.Label;

/**
 * Tests that a {@code Task}'s {@code Name} matches any of the keywords given.
 */
public class LabelMatchesKeywordPredicate implements Predicate<Task> {
    private final Label keyLabel;

    public LabelMatchesKeywordPredicate(String keyword) {
        // Standardises keyword toLowerCase such that the label predicate
        // is case-insensitive
        Label keyLabel = new Label(keyword.toLowerCase());
        this.keyLabel = keyLabel;
    }

    /**
     * Precondition: The predicate's keylabel should be in lowercase in order to
     * perform a case insensitive check.
     *
     * Checks the {@code Task}'s set of labels for a case-insensitive match.
     *
     * @param task to check for the interested label
     * @return True is the task contains the predicate's keyLabel
     */
    @Override
    public boolean test(Task task) {
        return task
            .getLabels()
            .stream()
            .anyMatch((label)->
                label.labelName.toLowerCase()
                    .equals(keyLabel.labelName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LabelMatchesKeywordPredicate // instanceof handles nulls
            && keyLabel
            .equals(((LabelMatchesKeywordPredicate) other).keyLabel)); // state check
    }

}
