package ua.org.zagoruiko.expenses.category.model;

import java.io.Serializable;

public enum SystemTag implements Serializable {
    SRC_PRIVAT_BANK(new Tag("SRC_PRIVAT_BANK", "Privat Bank")),
    SRC_ALFA_BANK(new Tag("SRC_ALFA_BANK", "Alfa Bank")),
    SRC_SPREADSHEETS(new Tag("SRC_SPREADSHEETS", "Old Google Spreadsheets")),
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
