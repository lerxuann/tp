package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupList;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

public class DeletePersonCommand extends DeleteCommand {
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    private final String personName;
    public DeletePersonCommand(String personName) {
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
        if (!(other instanceof DeletePersonCommand)) {
            return false;
        }

        DeletePersonCommand otherDeletePersonCommand = (DeletePersonCommand) other;
        return personName.equals(otherDeletePersonCommand.personName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", personName)
                .toString();
    }
}
