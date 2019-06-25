package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;

import java.util.Map;

public interface Matcher {
    TransactionMetadata metadata(Map<String, String> rawMetadata);
}
