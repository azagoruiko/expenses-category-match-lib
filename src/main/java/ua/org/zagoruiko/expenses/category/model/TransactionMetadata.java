package ua.org.zagoruiko.expenses.category.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TransactionMetadata {
    private final String category;
    private final Set<Tag> tags;

    public TransactionMetadata(String category, Set<Tag> tags) {
        this.category = category;
        this.tags = tags;
    }

    private TransactionMetadata(String category, Tag ... tags) {
        this.category = category;
        this.tags = new HashSet<>();
        this.tags.addAll(Arrays.asList(tags));
    }

    public String getCategory() {
        return category;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}
