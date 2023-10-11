package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedGroup {

    private final String groupName;

    /**
     * Constructs a {@code JsonAdaptedGroup} with the given {@code groupName}.
     */
    @JsonCreator
    public JsonAdaptedGroup(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedGroup(Group source) {
        groupName = source.getGroupName();
    }

    @JsonValue
    public String getTagName() {
        return groupName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Group toModelType() throws IllegalValueException {
        if (!Group.isValidGroup(groupName)) {
            throw new IllegalValueException("illegal value");
        }
        return new Group(groupName);
    }

}
