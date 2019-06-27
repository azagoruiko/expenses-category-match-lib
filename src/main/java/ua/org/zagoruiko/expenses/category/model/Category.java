package ua.org.zagoruiko.expenses.category.model;

public enum Category {
    UNKNOWN("UNKNOWN", "Unknown"),
    FOOD("FOOD", "Food and Drinks");

    private final String name;
    private final String friendlyName;

    Category(String name, String friendlyName) {
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
