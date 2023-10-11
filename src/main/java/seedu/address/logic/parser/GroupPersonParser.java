package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPTAG;

import java.util.stream.Stream;

import seedu.address.logic.commands.GroupPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class GroupPersonParser implements Parser<GroupPersonCommand> {

	/**
	 * Parses the given {@code String} of arguments in the context of the AddCommand
	 * and returns an AddCommand object for execution.
	 * @throws ParseException if the user input does not conform the expected format
	 */

	public GroupPersonCommand parse(String args) throws ParseException {
		String trimmedArgs = args.trim();
		if (trimmedArgs.isEmpty()) {
			throw new ParseException(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupPersonCommand.MESSAGE_USAGE));
		}

//		return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
		ArgumentMultimap argMultimap =
				ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUPTAG);


		if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUPTAG)
				|| !argMultimap.getPreamble().isEmpty()) {
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupPersonCommand.MESSAGE_USAGE));
		}
//		argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_GROUPTAG);
//		Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
		String personName = argMultimap.getValue(PREFIX_NAME).get();
		String groupName = argMultimap.getValue(PREFIX_GROUPTAG).get();

		return new GroupPersonCommand(
				personName, groupName);
	}

	/**
	 * Returns true if none of the prefixes contains empty {@code Optional} values in the given
	 * {@code ArgumentMultimap}.
	 */
	private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
		return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
	}


}
