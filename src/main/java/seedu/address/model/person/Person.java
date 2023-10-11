package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.exceptions.DuplicateGroupException;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;


    // Data fields
    private GroupList personGroups;


    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, GroupList personGroups) {
        requireAllNonNull(name, phone, email, personGroups);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.personGroups = personGroups;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public GroupList getGroups() {
        return personGroups;
    }

    /**
     * Adds group to persons existing groupList
     * @param group to be added to person groupList
     */
    public void addGroup(Group group) throws CommandException {
        requireNonNull(group);
        if (this.personGroups.contains(group)) {
            throw new CommandException(String.format("%s is already in this group: %s", this.name.fullName, group.getGroupName()));
        }
        this.personGroups.add(group);
    }

    /**
     * Removes group from persons existing groupList
     * @param group to be removed from person groupList
     */
    public void removeGroup(Group group) throws CommandException {
        requireNonNull(group);
        if (!this.personGroups.contains(group)) {
            throw new CommandException(String.format("%s is not in this group: %s", this.name.fullName, group.getGroupName()));
        }
        this.personGroups.remove(group);
    }

    /**
     * Check whether person is part of group
     * @param group group to check
     * @return boolean depending on whether person is in group
     */
    public boolean containsGroup(Group group) {
        return personGroups.contains(group);
    }


    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    public boolean nameEquals(String personName) {
        return name.nameEquals(personName);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && personGroups.equals(otherPerson.personGroups);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, personGroups);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("groups", personGroups)
                .toString();
    }

}
