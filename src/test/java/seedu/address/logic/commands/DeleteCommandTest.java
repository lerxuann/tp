package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String PERSONNAME_EXAMPLE = "Alex Yeoh";
    private String PERSONNAME_SECONDEXAMPLE = "Alex";

    @Test
    public void execute_validName_success() throws CommandException  {
        Person personToDelete = model.deletePerson(PERSONNAME_EXAMPLE);
        DeleteCommand deleteCommand = new DeleteCommand(PERSONNAME_EXAMPLE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete.getName().fullName);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() throws CommandException {
        Person personToDelete = model.deletePerson(PERSONNAME_EXAMPLE);
        DeleteCommand deleteCommand = new DeleteCommand(PERSONNAME_EXAMPLE);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_PERSON_WITH_NAME_FOUND);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(PERSONNAME_EXAMPLE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(PERSONNAME_SECONDEXAMPLE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(PERSONNAME_EXAMPLE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteCommand deleteCommand = new DeleteCommand(PERSONNAME_EXAMPLE);
        String expected = DeleteCommand.class.getCanonicalName() + "{name=" + PERSONNAME_EXAMPLE + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
