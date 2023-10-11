package seedu.address.model.group.exceptions;

/**
 * Exception thrown when Group specified can not be found
 */
public class GroupNotFoundException extends RuntimeException{
	public GroupNotFoundException() {
		super("Group not found");
	}
}
