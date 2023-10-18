package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupList;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person with the name provided.\n"
            + "Parameters: " + PREFIX_NAME
            + "NAME (must be the full name of a person in the existing contactlist)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Nicholas Lee";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_NO_PERSON_WITH_NAME_FOUND = "No one with such name found.\n"
            + "Please provide the person's full name as in the existing contactlist.";

    private final String personName;

    public DeleteCommand(String personName) {
        this.personName = personName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete = model.deletePerson(this.personName);

        //Delete person from all groups
        GroupList personGroups = personToDelete.getGroups();
        personGroups.toStream().forEach(g -> {
            try {
                g.removePerson(personToDelete);
                g.printGrpMates(); //for debugging purpose, prints the remaining user in each grp after del person
            } catch (CommandException e) {
                throw new RuntimeException();
            }
        });

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName().fullName));
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
        return personName.equals(otherDeleteCommand.personName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", personName)
                .toString();
    }
}
