package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.TaskManager;
import seedu.address.testutil.TypicalTasks;

public class XmlSerializableTaskManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTaskManagerTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksTaskManager.xml");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskManager.xml");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskTaskManager.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TASKS_FILE,
                XmlSerializableTaskManager.class);
        TaskManager taskManagerFromFile = dataFromFile.toModelType();
        TaskManager typicalTasksTaskManager = TypicalTasks.getTypicalTaskManager();
        assertEquals(taskManagerFromFile, typicalTasksTaskManager);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(INVALID_TASK_FILE,
                XmlSerializableTaskManager.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskManager dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_TASK_FILE,
                XmlSerializableTaskManager.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableTaskManager.MESSAGE_DUPLICATE_TASK);
        dataFromFile.toModelType();
    }

}
