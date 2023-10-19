package seedu.address.logic.commands;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class UngroupPersonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String PERSONNAME_EXAMPLE = "Alex Yeoh";
    private String PERSONNAME_BERNICE_EXAMPLE = "Bernice Yu";
    private String GROUPNAME_EXAMPLE = "CS2103T";
    private String GROUPNAME_ST2334_EXAMPLE = "ST2334";
    private String PERSONNAME_INCOMPLETE_EXAMPLE = "Alex";

    @Test
    public void validName_validGroup_success() throws CommandException {
        Pair<Person, Group> pairToUngroup = model.ungroupPerson(PERSONNAME_EXAMPLE, GROUPNAME_EXAMPLE);
        Person personToUngroup = pairToUngroup.getKey();
        Group groupToUngroup = pairToUngroup.getValue();
        String expectedMessage = String.format(UngroupPersonCommand.MESSAGE_SUCCESS,
                personToUngroup.getName().fullName);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.ungroupPerson(personToUngroup.getName().fullName, groupToUngroup.getGroupName());

        UngroupPersonCommand ungroupPersonCommand = new UngroupPersonCommand(PERSONNAME_EXAMPLE, GROUPNAME_EXAMPLE);

        assertCommandSuccess(ungroupPersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void invalidName_throwsCommandException() throws CommandException {
//        Pair<Person, Group> pairToUngroup = model.ungroupPerson(PERSONNAME_INCOMPLETE_EXAMPLE, GROUPNAME_EXAMPLE);
//        Person personToUngroup = pairToUngroup.getKey();
//        Group groupToUngroup = pairToUngroup.getValue();
        UngroupPersonCommand ungroupPersonCommand = new UngroupPersonCommand(PERSONNAME_INCOMPLETE_EXAMPLE, GROUPNAME_EXAMPLE);

        assertCommandFailure(ungroupPersonCommand, model, UngroupPersonCommand.MESSAGE_NO_PERSON_WITH_NAME_FOUND);
    }

    @Test
    public void equals() {
        UngroupPersonCommand ungroupPersonFirstCommand = new UngroupPersonCommand(PERSONNAME_EXAMPLE, GROUPNAME_EXAMPLE);
        UngroupPersonCommand ungroupPersonSecondCommand = new UngroupPersonCommand(PERSONNAME_BERNICE_EXAMPLE, GROUPNAME_EXAMPLE);
        UngroupPersonCommand ungroupPersonThirdCommand = new UngroupPersonCommand(PERSONNAME_EXAMPLE, GROUPNAME_ST2334_EXAMPLE);


        // same object -> returns true
        assertTrue(ungroupPersonFirstCommand.equals(ungroupPersonFirstCommand));

        // same values -> returns true
        UngroupPersonCommand ungroupPersonFirstCommandCopy = new UngroupPersonCommand(PERSONNAME_EXAMPLE, GROUPNAME_EXAMPLE);
        assertTrue(ungroupPersonFirstCommand.equals(ungroupPersonFirstCommandCopy));

        // different types -> returns false
        assertFalse(ungroupPersonFirstCommand.equals(1));

        // null -> returns false
        assertFalse(ungroupPersonFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(ungroupPersonFirstCommand.equals(ungroupPersonSecondCommand));

        // different group -> returns false
        assertFalse(ungroupPersonFirstCommand.equals(ungroupPersonThirdCommand));
    }

    @Test
    public void toStringMethod() {
        UngroupPersonCommand ungroupPersonCommand = new UngroupPersonCommand(PERSONNAME_EXAMPLE, GROUPNAME_EXAMPLE);
        String expected = UngroupPersonCommand.class.getCanonicalName() +
                "{personName=" + PERSONNAME_EXAMPLE + ", " +
                "groupName=" + GROUPNAME_EXAMPLE + "}";
        assertEquals(expected, ungroupPersonCommand.toString());
    }
}
