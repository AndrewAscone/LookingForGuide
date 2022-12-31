package rocks.zipcode.domain.enumeration;

/**
 * The GuideType enumeration.
 */
public enum GuideType {
    TUTORIAL("Tutorial"),
    RAID("Raid");

    private final String value;

    GuideType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
