package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

public class DeleteGroupCommand extends DeleteCommand{
    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    private final String groupName;
    public DeleteGroupCommand(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Group groupToDelete = model.deleteGroup(this.groupName);

        //Delete group from all People
        ObservableList<Person> groupMates = groupToDelete.getListOfGroupMates();
        groupMates.stream().forEach(p -> {
            try {
                p.removeGroup(groupToDelete);
            } catch (CommandException e) {
                throw new RuntimeException();
            }
        });

        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete.getName()));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGroupCommand)) {
            return false;
        }

        DeleteGroupCommand otherDeleteGroupCommand = (DeleteGroupCommand) other;
        return groupName.equals(otherDeleteGroupCommand.groupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("group name", groupName)
                .toString();
    }
}
