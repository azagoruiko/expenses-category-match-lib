package ua.org.zagoruiko.expenses.category.model;

public enum Tag {
    SRC_PRIVAT_BANK("SRC_PRIVAT_BANK", "Privat Bank"),
    SRC_CASH("SRC_CASH", "Cash"),

    TAG_FOZZY("FOZZY", "Fozzy market"),
    TAG_SUPERMARKET("SUPERMARKET", "Some market"),
    TAG_HAS_DRINKS("HAS_DRINKS", "Purchase includes drinks"),

    TAG_TAXI("TAXI", "Taxi"),
    TAG_TRANSPORT("TRANSPORT", "Transport");

    private final String name;
    private final String friendlyName;

    Tag(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public String getName() {
        return name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
