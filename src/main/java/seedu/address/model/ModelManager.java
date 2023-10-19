package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Group> filteredGroups;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean[] usedFields(Person person) {
        requireNonNull(person);
        return addressBook.usedFields(person);
    }

    @Override
    public Person deletePerson(String personName) throws CommandException {
        Person person =addressBook.getPerson(personName);
        addressBook.removePerson(person);
        return person;
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns true if a group with the same identity as {@code group} exists in the address book.
     */
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return addressBook.hasGroup(group);
    }

    /**
     * Adds a group to the address book.
     * The group must not already exist in the address book.
     */
    public void addGroup(Group group) {
        addressBook.addGroup(group);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public Group deleteGroup(String groupName) throws CommandException {
        Group group =addressBook.getGroup(groupName);
        addressBook.removeGroup(group);
        return group;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return filteredGroups;
    };



    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // in GroupPersonCommand call model.groupPerson(person, group) note that these are simply strings!
    // groupPerson(person, group)
    // calls addressbook.groupPerson(person, group)
    // addressbook -> personlist grouplist
    // method in addressbook referencing method in personList groupList (oop) set person to group and group to person
    // get
    // get
    // person.setgroup(group)
    // person already in group throw exception also
    // group.setperson(person)
    // group already contain person
    // exactPersonExist exactGroupExist
    // better to retrieve both person and group object first then set else error prone
    // for loop all things once name exact match for person / group
    // this methods throws command exception, yes it works
    @Override
    public Pair<Person, Group> groupPerson(String personName, String groupName) throws CommandException {
        // both throw exception if not exists exact match
        Person person =addressBook.getPerson(personName);
        Group group = addressBook.getGroup(groupName);
        this.assignGroup(person, group);
        forceUpdateList();
        Pair<Person, Group> output = new Pair<>(person, group);
        return output;
    }

    /**
     * Assign person to group
     * @param person person to be grouped
     * @param group group in consideration
     * @throws CommandException if person has already been assigned to group
     */
    private void assignGroup(Person person, Group group) throws CommandException {
       group.addPerson(person);
       person.addGroup(group);
    }

    @Override
    public Pair<Person, Group> ungroupPerson(String personName, String groupName) throws CommandException {
        // both throw exception if not exists exact match
        Person person = addressBook.getPerson(personName);
        Group group = addressBook.getGroup(groupName);
        this.unassignGroup(person, group);
        forceUpdateList();
        Pair<Person, Group> output = new Pair<>(person, group);
        return output;
    }

    /**
     * Assign person to group
     * @param person person to be grouped
     * @param group group in consideration
     * @throws CommandException if person has already been assigned to group
     */
    private void unassignGroup(Person person, Group group) throws CommandException {
        group.removePerson(person);
        person.removeGroup(group);
    }

    private void forceUpdateList() {
        updateFilteredPersonList(user -> false);
        updateFilteredPersonList(user -> true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
