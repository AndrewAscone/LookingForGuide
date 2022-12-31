package rocks.zipcode.domain.enumeration;

/**
 * The UserType enumeration.
 */
public enum UserType {
    MENTEE("Student"),
    MENTOR("Guide");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
