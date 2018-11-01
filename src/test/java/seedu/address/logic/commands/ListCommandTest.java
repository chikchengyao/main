package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.J_TASK;
import static seedu.address.testutil.TypicalTasks.K_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskManager;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.DueDateIsBeforeEndOfMonthPredicate;
import seedu.address.model.task.DueDateIsBeforeEndOfWeekPredicate;
import seedu.address.model.task.DueDateIsBeforeTodayPredicate;
import seedu.address.model.task.Task;
import seedu.address.model.util.DateFormatUtil;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskManagerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTaskManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listFiltered_beforeToday() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        ListCommand.ListFilter filter = ListCommand.ListFilter.DUE_TODAY;
        ListCommand command = new ListCommand(filter);

        TaskManager manager = (new TaskManagerBuilder())
                .withTask(J_TASK)
                .withTask(K_TASK)
                .build();
        // Build new ModelManagers to handle temporal differences
        ModelManager modelWithExtremeTemporalTasks =
                new ModelManager(manager, new UserPrefs());
        ModelManager expectedModelWithPastTask =
                new ModelManager(modelWithExtremeTemporalTasks.getTaskManager(), new UserPrefs());
        expectedModelWithPastTask.updateFilteredTaskList(new DueDateIsBeforeTodayPredicate());

        assertCommandSuccess(command, modelWithExtremeTemporalTasks, commandHistory,
                expectedMessage, expectedModelWithPastTask);
        assertEquals(Arrays.asList(J_TASK), modelWithExtremeTemporalTasks.getFilteredTaskList());
    }

    @Test
    public void execute_listFiltered_beforeEndOfWeek() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        ListCommand.ListFilter filter = ListCommand.ListFilter.DUE_END_OF_WEEK;
        ListCommand command = new ListCommand(filter);

        /** Get a string representation for a time almost at the end of the week. */
        Calendar c = Calendar.getInstance();
        c = new Calendar.Builder()
                .setWeekDate(c.get(Calendar.YEAR), c.get(Calendar.WEEK_OF_YEAR), Calendar.SUNDAY)
                .setTimeOfDay(20, 00, 00)
                .build();
        Date date = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY HHmm");
        String strDate = formatter.format(date);

        /** Build a task with the due date as the end of the week*/
        Task taskDueThisWeek = new TaskBuilder()
                .withName("Finish my homework due this week")
                .withDueDate(strDate)
                .build();
        TaskManager manager = (new TaskManagerBuilder())
                .withTask(J_TASK)
                .withTask(K_TASK)
                .withTask(taskDueThisWeek)
                .build();
        // Build new ModelManagers to handle temporal differences
        ModelManager modelWithExtremeTemporalTasks =
                new ModelManager(manager, new UserPrefs());
        ModelManager expectedModelWithPastTask =
                new ModelManager(modelWithExtremeTemporalTasks.getTaskManager(), new UserPrefs());
        expectedModelWithPastTask.updateFilteredTaskList(new DueDateIsBeforeEndOfWeekPredicate());

        assertCommandSuccess(command, modelWithExtremeTemporalTasks, commandHistory,
                expectedMessage, expectedModelWithPastTask);
        assertEquals(Arrays.asList(J_TASK, taskDueThisWeek), modelWithExtremeTemporalTasks.getFilteredTaskList());
    }

    @Test
    public void execute_listFiltered_beforeEndOfMonth() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        ListCommand.ListFilter filter = ListCommand.ListFilter.DUE_END_OF_MONTH;
        ListCommand command = new ListCommand(filter);

        /** Get a string representation for a time almost at the end of the month. */
        Calendar c = Calendar.getInstance();
        c = new Calendar.Builder()
                .setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH) - 1)
                .setTimeOfDay(20, 00, 00)
                .build();
        Date date = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormatUtil.DATE_FORMAT_STANDARD);
        String strDate = formatter.format(date);

        /** Build a task with the due date near the end of the month */
        Task taskDueThisMonth = new TaskBuilder()
                .withName("Finish my project due this month")
                .withDueDate(strDate)
                .build();
        TaskManager manager = (new TaskManagerBuilder())
                .withTask(J_TASK)
                .withTask(K_TASK)
                .withTask(taskDueThisMonth)
                .build();
        // Build new ModelManagers to handle temporal differences
        ModelManager modelWithExtremeTemporalTasks =
                new ModelManager(manager, new UserPrefs());
        ModelManager expectedModelWithPastTask =
                new ModelManager(modelWithExtremeTemporalTasks.getTaskManager(), new UserPrefs());
        expectedModelWithPastTask.updateFilteredTaskList(new DueDateIsBeforeEndOfMonthPredicate());

        assertCommandSuccess(command, modelWithExtremeTemporalTasks, commandHistory,
                expectedMessage, expectedModelWithPastTask);
        assertEquals(Arrays.asList(J_TASK, taskDueThisMonth), modelWithExtremeTemporalTasks.getFilteredTaskList());
    }
}
