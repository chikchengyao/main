package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * A task is considered unique by comparing using {@code Task#isSameTask(Task)}. As such, adding and updating of
 * tasks uses Task#isSameTask(Task) for equality so as to ensure that the task being added or updated is
 * unique in terms of identity in the UniqueTaskList. However, the removal of a task uses Task#equals(Object) so
 * as to ensure that the task with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Task#isSameTask(Task)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a task to the list.
     * The task must not already exist in the list.
     */
    public void add(Task toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the list.
     * The task identity of {@code editedTask} must not be the same as another existing task in the list.
     */
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        if (!target.isSameTask(editedTask) && contains(editedTask)) {
            throw new DuplicateTaskException();
        }
        String oldHash = Integer.toString(target.hashCode());
        String newHash = Integer.toString(editedTask.hashCode());
        if (!oldHash.equals(newHash)) {
            for (int i = 0; i < internalList.size(); i++) {
                Task task = internalList.get(i);
                if (task.isDependentOn(target)) {
                    Task newTask = createUpdatedHashReferenceTask(task, oldHash, newHash);
                    internalList.set(i, newTask);
                }
            }
        }

        internalList.set(index, editedTask);
    }

    /**
     * When a task is edited, a similar task with a new hashcode is generated. Tasks that are dependant
     * on the edited task should have their task dependency hashcode updated.
     * @param taskToEdit task with dependency to be updated to that of the new hash
     * @param oldHash old hash of task
     * @param newHash new hash of task
     * @return new task with dependency updated
     */
    private Task createUpdatedHashReferenceTask(Task taskToEdit, String oldHash, String newHash) {
        return new Task(taskToEdit.getName(), taskToEdit.getDueDate(), taskToEdit.getPriorityValue(),
                taskToEdit.getDescription(), taskToEdit.getLabels(), taskToEdit.getStatus(),
                taskToEdit.getDependency().updateHash(oldHash, newHash));
    }

    /**
     * Removes the equivalent task from the list.
     * The task must exist in the list.
     */
    public void remove(Task toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TaskNotFoundException();
        }
        //Remove all dependencies on task to be remove
        for (int i = 0; i < internalList.size(); i++) {
            Task task = internalList.get(i);
            if (task.isDependentOn(toRemove)) {
                Task newTask = createUndependantTask(task, toRemove);
                internalList.set(i, newTask);
            }
        }
    }

    public void setTasks(UniqueTaskList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
        if (!tasksAreUnique(tasks)) {
            throw new DuplicateTaskException();
        }

        internalList.setAll(tasks);
    }
    //==============Check overdue state of tasks =============================
    /**
     * Checks overdue state of task and updates all relevant tasks
     */
    public void updateOverdue() {
        for (int i = 0; i < internalList.size(); i++) {
            Task task = internalList.get(i);
            if (task.isStatusOverdue() && !task.isStatusCompleted()) {
                Task updatedTask = createOverdueTask(task);
                setTask(task, updatedTask);
            }
        }
    }

    /**
     * Creates a task with an OVERDUE status
     * @param taskToEdit
     * @return
     */
    private Task createOverdueTask(Task taskToEdit) {
        return new Task(taskToEdit.getName(), taskToEdit.getDueDate(), taskToEdit.getPriorityValue(),
                taskToEdit.getDescription(), taskToEdit.getLabels(), Status.OVERDUE,
                taskToEdit.getDependency());
    }

    /**
     * Returns a {@code Task} with the dependency removed.
     * @param dependantTask An immutable task passed to have its attributes copied
     * @return A new immutable task similar to dependantTask but without dependency to dependee
     */
    public static Task createUndependantTask(Task dependantTask, Task dependeeTask) {
        return new Task(
                dependantTask.getName(),
                dependantTask.getDueDate(),
                dependantTask.getPriorityValue(),
                dependantTask.getDescription(),
                dependantTask.getLabels(),
                dependantTask.getStatus(),
                dependantTask.getDependency().removeDependency(dependeeTask)
        );
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tasks} contains only unique tasks.
     */
    private boolean tasksAreUnique(List<Task> tasks) {
        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).isSameTask(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
