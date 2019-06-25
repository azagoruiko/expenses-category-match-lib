package ua.org.zagoruiko.expenses.category.model;

import java.util.Set;

public class TransactionMetadata {
    private final String category;
    private final Set<String> tags;

    private TransactionMetadata(String category, Set<String> tags) {
        this.category = category;
        this.tags = tags;
    }
}
