package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.GroupList;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person with the name provided.\n"
            + "Parameters: NAME (must be the full name of a person in the existing contactlist)\n"
            + "Example: " + COMMAND_WORD + " Nicholas Lee";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_NO_PERSON_WITH_NAME_FOUND = "No one with such name found.\n"
            + "Please provide the person's full name as in the existing contactlist.";

    private final NameContainsKeywordsPredicate predicate;

    public DeleteCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        ObservableList<Person> filteredPersons = model.getFilteredPersonList();

        if (filteredPersons.size() < 1) {
            throw new CommandException(MESSAGE_NO_PERSON_WITH_NAME_FOUND);
        } else if (filteredPersons.size() > 1) {
            filteredPersons = filteredPersons.sorted();
        }

        Person personToDelete = filteredPersons.get(0);
        String[] nameWords = personToDelete.getName().toString().split("\\s+");
        if (!predicate.equals(new NameContainsKeywordsPredicate(Arrays.asList(nameWords)))) {
            throw new CommandException(MESSAGE_NO_PERSON_WITH_NAME_FOUND);
        }

        //Delete person from all groups
        GroupList personGroups = personToDelete.getGroups();
        personGroups.toStream().forEach(g -> g.remove(personToDelete));

        //Delete person from address book
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return predicate.equals(otherDeleteCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", predicate)
                .toString();
    }
}
