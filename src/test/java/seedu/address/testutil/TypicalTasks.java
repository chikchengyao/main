package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskManager;
import seedu.address.model.person.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task A_TASK = new TaskBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withPriorityValue("alice@example.com")
            .withDueDate("01-12-18")
            .withLabels("friends").build();
    public static final Task B_TASK = new TaskBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withPriorityValue("johnd@example.com").withDueDate("02-12-18 1330")
            .withLabels("owesMoney", "friends").build();
    public static final Task C_TASK = new TaskBuilder().withName("Carl Kurz").withDueDate("03-12-18")
            .withPriorityValue("heinz@example.com").withDescription("wall street").build();
    public static final Task D_TASK = new TaskBuilder().withName("Daniel Meier").withDueDate("04-12-18")
            .withPriorityValue("cornelia@example.com").withDescription("10th street").withLabels("friends").build();
    public static final Task E_TASK = new TaskBuilder().withName("Elle Meyer").withDueDate("05-12-18")
            .withPriorityValue("werner@example.com").withDescription("michegan ave").build();
    public static final Task F_TASK = new TaskBuilder().withName("Fiona Kunz").withDueDate("06-12-2018")
            .withPriorityValue("lydia@example.com").withDescription("little tokyo").build();
    public static final Task G_TASK = new TaskBuilder().withName("George Best").withDueDate("07-12-18")
            .withPriorityValue("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Task H_TASK = new TaskBuilder().withName("Hoon Meier").withDueDate("08-12-18 0900")
            .withPriorityValue("stefan@example.com").withDescription("little india").build();
    public static final Task I_TASK = new TaskBuilder().withName("Ida Mueller").withDueDate("09-12-18")
            .withPriorityValue("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task Y_TASK = new TaskBuilder().withName(VALID_NAME_AMY).withDueDate(VALID_DUEDATE_AMY)
            .withPriorityValue(VALID_EMAIL_AMY).withDescription(VALID_ADDRESS_AMY).withLabels(VALID_TAG_FRIEND).build();
    public static final Task Z_TASK = new TaskBuilder().withName(VALID_NAME_BOB).withDueDate(VALID_DUEDATE_BOB)
            .withPriorityValue(VALID_EMAIL_BOB).withDescription(VALID_ADDRESS_BOB).withLabels(VALID_TAG_HUSBAND,
                    VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code TaskManager} with all the typical tasks.
     */
    public static TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        for (Task task : getTypicalTasks()) {
            tm.addTask(task);
        }
        return tm;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(A_TASK, B_TASK, C_TASK, D_TASK, E_TASK, F_TASK, G_TASK));
    }
}