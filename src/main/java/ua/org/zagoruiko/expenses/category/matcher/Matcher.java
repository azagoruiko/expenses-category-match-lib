package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;

import java.io.Serializable;
import java.util.Map;

public interface Matcher extends Serializable {
    TransactionMetadata metadata(Map<String, String> rawMetadata);
}
