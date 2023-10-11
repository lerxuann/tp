package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPTAG;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.Model;

/**
 * Adds a group to projectPRO
 */
public class CreateGroupCommand extends Command {
    public static final String COMMAND_WORD = "new";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a group to the address book. "
            + "Parameters: "
            + PREFIX_GROUPTAG + "GROUPNAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUPTAG + "CS2103T";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final Group toAdd;

    /**
     * Creates an newCommand to add the specified {@code Group}
     */
    public CreateGroupCommand(Group group) {
        requireNonNull(group);
        toAdd = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }
}
