package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupList;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person with the name provided.\n"
            + "Parameters: " + PREFIX_NAME
            + "NAME (must be the full name of a person in the existing contactlist)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Nicholas Lee";

    public static final String MESSAGE_NO_PERSON_WITH_NAME_FOUND = "No one with such name found.\n"
            + "Please provide the person's full name as in the existing contactlist.";


    public DeleteCommand() {}

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
