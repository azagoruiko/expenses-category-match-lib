package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PbMatcher implements Matcher {
    private final Map<String, String> categoryMatch = new HashMap<>();
    private final Map<String, String> exactMatch = new HashMap<>();

    public PbMatcher() {
        this.categoryMatch.put("Продукты питания", "Food and Drinks");
        this.categoryMatch.put("Кафе, бары, рестораны", "Cafe and Restaurants");
        this.categoryMatch.put("Развлечения", "Entertainment");
        this.categoryMatch.put("Коммунальные услуги", "Utilities");
        this.categoryMatch.put("Пополнение мобильного", "Utilities");
    }

    private String categoryFromOperationDesc(String operationDesc) {
        if (operationDesc.contains("WFPTAXI")) {
            return "Transport";
        }
        return "Unknown";
    }

    private Set<Tag> tagsFromOperationDesc(String operationDesc) {
        Set<Tag> tags = new HashSet<>();
        if (operationDesc.contains("WFPTAXI")) {
            tags.add(Tag.TAG_TAXI);
        }

        if (operationDesc.contains("fozzy")) {
            tags.add(Tag.TAG_FOZZY);
        }
        return tags;
    }

    @Override
    public TransactionMetadata metadata(Map<String, String> rawMetadata) {
        String category = this.categoryMatch.get(rawMetadata.get("category"));
        if (category == null) {
            category = categoryFromOperationDesc(rawMetadata.get("operation"));
        }

        Set<Tag> tags = tagsFromOperationDesc(rawMetadata.get("operation"));
        tags.add(Tag.SRC_PRIVAT_BANK);

        return new TransactionMetadata(category, tags);
    }
}
