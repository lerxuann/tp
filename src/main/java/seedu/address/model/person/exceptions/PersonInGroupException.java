package seedu.address.model.person.exceptions;

public class PersonInGroupException extends RuntimeException {
	public PersonInGroupException() {
		super("Person is already assigned to group");
	}

}
