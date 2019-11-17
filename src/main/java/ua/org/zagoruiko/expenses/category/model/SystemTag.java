package ua.org.zagoruiko.expenses.category.model;

public enum SystemTag {
    SRC_PRIVAT_BANK(new Tag("SRC_PRIVAT_BANK", "Privat Bank")),
    SRC_CASH(new Tag("SRC_CASH", "Cash")),

    TAG_FOZZY(new Tag("FOZZY", "Fozzy market")),
    TAG_SUPERMARKET(new Tag("SUPERMARKET", "Some market")),
    TAG_HAS_DRINKS(new Tag("HAS_DRINKS", "Purchase includes drinks")),

    TAG_TAXI(new Tag("TAXI", "Taxi")),
    TAG_TRANSPORT(new Tag("TRANSPORT", "Transport")),
    TAG_EAT_OUT(new Tag("EAT_OUT", "Eat Out")),
    TAG_YIDALNYA(new Tag("YIDALNYA", "Stolovaya Buffet"));

    private final Tag tag;

    SystemTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return this.tag;
    }
}