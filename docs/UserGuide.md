---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# ProjectPRO User Guide

Text-friendly project management tool that helps students schedule meetings with different groups while also keeping track of tasks and responsibilities of each member. Our app will track the schedule of each contact and tasks individuals have to do for their project.

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `ProjectPRO.jar` from [here](https://github.com/AY2324S1-CS2103T-T10-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ProjectPRO.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com` : Adds a contact named `John Doe` to the contact list.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a person: `add`
Adds a person to the contact list.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [g/GROUPNAME]​`

<box type="tip" seamless>

**Tip:** A person can be added with or without a group
</box>

Acceptable values: phone number should be an 8 digit integer, while other parameters be strings.

Names should be unique, no two contacts should have the same name input.

Expected output when succeeds: “Contact successfully added”

Expected output when fails: “Error, invalid input entered”

Examples:
* `add n/Adam Lee p/81112222 e/adam@example.com g/cs2103 g/cs2101`
* `add n/Brian p/98765432 e/brian@example.com`


### Listing all persons : `list`

Shows a list of all persons in the contact list.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the contact list.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified contact from the contact list. (This contact will also be removed from any existing groups.)

Format: `delete NAME`
* Deletes the contact with the specified NAME
* NAME has to be unique (no existing contact with same name)
* NAME has to be an exact match

Examples:
* `delete Gerald (cs2101)`
* `delete jane`

Acceptable values: `NAME should be a string.`

Expected output when succeeds: `Contact successfully deleted`

Expected output when fails: `Error, invalid input entered`

### Add contacts to groups: `group`

Add a contact to a group.

Format: `group n/NAME g/GROUPNAME`

* Add the contact with given `NAME` from the given `GROUPNAME`
* If group or name does not exist, print error

Acceptable values:
* `NAME` and `GROUPNAME` are strings

Example(s):
* `group n/John g/cs2101 tut`
* `group n/samantha tan g/saturday 9am yoga`

Expected outputs when it succeeds: `John is now part of group cs2101.`
Expected output when fails: `Error, invalid input entered`

### Remove contacts from groups: `ungroup`

Removes a contact from a group.

Format: `ungroup n/NAME g/GROUPNAME`

* Deletes the contact with given `NAME` from the given `GROUPNAME`
* If group or name does not exist, print error

Acceptable values:
* `NAME` and `GROUPNAME` are strings

Example(s): 
* `ungroup n/John g/cs2101 tut`
* `ungroup n/samantha tan g/saturday 9am yoga`

Expected outputs when it succeeds: `John has been removed from cs2101 tut.`
Expected output when fails: `Error, invalid input entered`

### Create a new group : `new`

Creates a new group with group name provided.

Format: `new g/GROUPNAME`
* Creates a new group with group name provided. 
* GROUPNAME has to be unique (not an existing group)

Expected output when succeeds: New group added: GROUPNAME<br>

Expected output when fails: This group already exists in the contact list.<br>

Expected output when fails: Invalid command format

Examples:
* `new g/CS2103T` Creates a new group named CS2103T
* `new g/CS2101_OP1` Creates a new group named CS2101_OP1


### Clearing all entries : `clear`

Clears all entries from the contact list.

Format: `clear`

Expected output when succeeds: Address book has been cleared!<br>

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

All data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

All data is saved automatically as a JSON file. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ProjectPRO will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ProjectPRO home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL [g/GROUPNAME]` <br> e.g., `add n/Adam Lee p/81112222 e/jamesho@example.com, g/cs2103`
**Clear**  | `clear`
**Delete** | `delete NAME`<br> e.g., `delete nicholas`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Group**   | `group n/NAME g/GROUPNAME`<br> e.g., `group n/John g/cs2103 tut` 
**Ungroup**   | `ungroup n/NAME g/GROUPNAME`<br> e.g., `ungroup n/John g/cs2103 tut` 
**New**    | `new g/GROUPNAME`<br> e.g., `new g/cs2103`
**List**   | `list`
**Help**   | `help`
