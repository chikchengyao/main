package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import seedu.address.testutil.TaskBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DependencyTest {
    private Dependency SAMPLE_DEPENDENCY;
    private Set<String> SAMPLE_SET;
    private Task SAMPLE_TASK_IN_DEPENDENCY;

    @Before
    public void setUp() {
        HashSet<String> hashes = new HashSet<String>();

        hashes.add("12345");
        hashes.add("67890");
        SAMPLE_TASK_IN_DEPENDENCY = new TaskBuilder().withName("Test").build();
        SAMPLE_DEPENDENCY = new Dependency(hashes);
        SAMPLE_SET = hashes;


    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dependency(null));
    }

    @Test
    public void addDependency_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dependency().addDependency(null));
    }


    @Test
    public void addDependency() {
        Task toBeAdded = new TaskBuilder().build();
        //Creating expected dependency
        SAMPLE_SET.add(Integer.toString(toBeAdded.hashCode()));
        Dependency expectedDependency = new Dependency(SAMPLE_SET);
        //Checking equality
        assertEquals(expectedDependency, SAMPLE_DEPENDENCY.addDependency(toBeAdded));
    }

    }

}
