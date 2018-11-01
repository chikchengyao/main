package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.TaskBuilder;

public class LabelMatchesKeywordPredicateTest {

    @Test
    public void equals() {
        String firstDummyKeyword = "habibi";
        String secondDummyKeyword = "hablahabla";
        LabelMatchesKeywordPredicate firstPredicate = new LabelMatchesKeywordPredicate(firstDummyKeyword);
        LabelMatchesKeywordPredicate secondPredicate = new LabelMatchesKeywordPredicate(secondDummyKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LabelMatchesKeywordPredicate firstPredicateCopy = new LabelMatchesKeywordPredicate(firstDummyKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same keywords, case insensitive -> returns true
        String firstDummyKeywordCaseInsensitive = "hAbiBI";
        LabelMatchesKeywordPredicate firstPredicateCaseInsensitive =
            new LabelMatchesKeywordPredicate(firstDummyKeywordCaseInsensitive);
        assertTrue(firstPredicate.equals(firstPredicateCaseInsensitive));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_labelMatchesKeyword_returnsTrue() {
        // One Label
        LabelMatchesKeywordPredicate predicate = new LabelMatchesKeywordPredicate("Urgent");
        assertTrue(predicate.test(new TaskBuilder().withLabels("Urgent").build()));

        // Multiple Labels
        assertTrue(predicate.test(new TaskBuilder().withLabels("Urgent", "Critical", "VeryReallyUrgent").build()));

        // Case-Insensitive Variations
        assertTrue(predicate.test(new TaskBuilder().withLabels("UrGeNt").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        LabelMatchesKeywordPredicate predicate = new LabelMatchesKeywordPredicate("Severe");
        assertFalse(predicate.test(new TaskBuilder().withLabels("Urgent").build()));

        // Keywords match priority value, description and name, but does not match label
        // DueDate is not tested as Label's format cannot match DueDate's
        predicate = new LabelMatchesKeywordPredicate("2");
        assertFalse(predicate.test(new TaskBuilder().withPriorityValue("2").build()));

        predicate = new LabelMatchesKeywordPredicate("Milestone1");
        assertFalse(predicate.test(new TaskBuilder().withName("Milestone1").build()));

        predicate = new LabelMatchesKeywordPredicate("Donoteat");
        assertFalse(predicate.test(new TaskBuilder().withDescription("Donoteat").build()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_emptyString_throwsException() {
        // Empty string as a keyword
        LabelMatchesKeywordPredicate predicate = new LabelMatchesKeywordPredicate("");
    }

}
