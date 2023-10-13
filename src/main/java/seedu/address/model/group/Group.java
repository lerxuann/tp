package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Class representing a group
 */
public class Group {
    public static final String MESSAGE_CONSTRAINTS = "Group names should be alphanumeric";
    private final ObservableList<Person> listOfGroupMates = FXCollections.observableArrayList();
    private final String groupName;

    /**
     * Name field must be present and not null.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null
                && otherGroup.getGroupName().equals(getGroupName());
    }

    /**
     * Check if same group according to name since groupName is unique
     * @param groupName of interest
     * @return whether group is the same group
     */
    public boolean nameEquals(String groupName) {
        return this.groupName.equals(groupName);
    }

    /**
     * Returns if the name of the group is valid.
     * @param name The name of the group
     * @return The validity of the group name.
     */
    //For now no constraints
    public static boolean isValidGroup(String name) {
        return true;
    }

    /**
     * Removes the person from the group.
     * The person must exist in the group.
     */
    public void removePerson(Person toRemove) throws CommandException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new CommandException(String.format("%s is not in this group: %s", toRemove.getName().fullName, this.groupName));
        }
        listOfGroupMates.remove(toRemove);
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return listOfGroupMates.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void addPerson(Person personToAdd) throws CommandException {
        requireNonNull(personToAdd);
        if (this.contains(personToAdd)) {
            throw new CommandException(String.format("%s is already in this group: %s", personToAdd.getName().fullName, this.groupName));
        }
        listOfGroupMates.add(personToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Group name", groupName)
                .toString();
    }

    public void printGrpMates() {
        this.listOfGroupMates.forEach(x -> System.out.println(x.getName()));
    }

    @Override
    public boolean equals(Object group) {
        if (group == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(group instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) group;
        return this.groupName.equals(otherGroup.getGroupName());
    }

    public String getName() {
        return this.groupName;
    }
}
