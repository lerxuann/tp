package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameEqualsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public NameEqualsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getName().fullName.equals(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameEqualsKeywordPredicate)) {
            return false;
        }

        NameEqualsKeywordPredicate otherNameEqualsKeywordPredicate = (NameEqualsKeywordPredicate) other;
        return keyword.equals(otherNameEqualsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
