package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list containing groups
 */
public class GroupList implements Iterable<Group> {

    private ObservableList<Group> internalList = FXCollections.observableArrayList();
    private final ObservableList<Group> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent group as the given argument.
     */
    public boolean contains(Group toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameGroup);
    }

    /**
     * Adds a group to the list.
     * The group must not already exist in the list.
     */
    public void add(Group toAdd) {
        requireNonNull(toAdd);
        if (this.contains(toAdd)) {
            throw new DuplicateGroupException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent group from the list.
     * The group must exist in the list.
     */
    public void remove(Group toRemove) {
        requireNonNull(toRemove);
        if (!this.contains(toRemove)) {
            throw new GroupNotFoundException();
        }
        internalList.remove(toRemove);
    }

    /**
     * Converts the internal list to streams.
     * @return Internal list into streams.
     */
    public Stream<Group> toStream() {
        return internalList.stream();
    }

    @Override
    public Iterator<Group> iterator() {
        return internalList.iterator();
    }
    public ObservableList<Group> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupList)) {
            return false;
        }

        GroupList otherGroupList = (GroupList) other;
        return internalList.equals(otherGroupList.internalList);
    }

    public Group getGroup(String groupName) throws CommandException {
        for (Group group: this.internalList) {
            if (group.nameEquals(groupName)) {
                return group;
            }
        }
        throw new CommandException("Group does not exist");
    }



    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

}
