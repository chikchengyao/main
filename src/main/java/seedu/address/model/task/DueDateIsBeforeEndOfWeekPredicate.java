package seedu.address.model.task;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s {@code DueDate} is before the end of today.
 */
public class DueDateIsBeforeEndOfWeekPredicate implements Predicate<Task> {

    @Override
    public boolean test(Task person) {
        /** Get time at the end of the week, Sunday 2359. */
        Calendar date = new GregorianCalendar();
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return person.getDueDate().valueDate.before(date.getTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDateIsBeforeEndOfWeekPredicate); // instanceof handles nulls
    }

}
