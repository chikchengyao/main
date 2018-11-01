package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ListCommandParser.DUE_END_OF_MONTH_OPTION;
import static seedu.address.logic.parser.ListCommandParser.DUE_END_OF_WEEK_OPTION;
import static seedu.address.logic.parser.ListCommandParser.DUE_TODAY_OPTION;
import static seedu.address.logic.parser.ListCommandParser.PREFIX_DUE_BEFORE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.DueDateIsBeforeEndOfMonthPredicate;
import seedu.address.model.task.DueDateIsBeforeEndOfWeekPredicate;
import seedu.address.model.task.DueDateIsBeforeTodayPredicate;
import seedu.address.model.task.Task;



/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks. "
            + "Parameters: "
            + "[" + PREFIX_DUE_BEFORE + "DUE BEFORE " + "]\n"
            + "Allowed values for \"DUE BEFORE\": "
            + DUE_TODAY_OPTION + ", "
            + DUE_END_OF_WEEK_OPTION + ", "
            + DUE_END_OF_MONTH_OPTION + ", " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DUE_BEFORE + "today";

    private final Predicate<Task> predicate;

    /**
     * Denotes the kind of filters List supports.
     */
    public enum ListFilter {
            DUE_TODAY,
            DUE_END_OF_WEEK,
            DUE_END_OF_MONTH;
    }

    public ListCommand() {
        this.predicate = PREDICATE_SHOW_ALL_TASKS;
    }

    public ListCommand(ListFilter listFilter) {
        switch (listFilter) {
        case DUE_TODAY:
            this.predicate = new DueDateIsBeforeTodayPredicate();
            break;
        case DUE_END_OF_WEEK:
            this.predicate = new DueDateIsBeforeEndOfWeekPredicate();
            break;
        case DUE_END_OF_MONTH:
            this.predicate = new DueDateIsBeforeEndOfMonthPredicate();
            break;
        default:
            this.predicate = PREDICATE_SHOW_ALL_TASKS;
            break;
        }
    }


    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(this.predicate);
        return model.getFilteredTaskList().size() == model.getTaskManager().getTaskList().size()
                ? new CommandResult(MESSAGE_SUCCESS)
                : new CommandResult(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                    model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
