package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.Tag;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface Matcher extends Serializable {
    Set<Tag> metadata(Map<String, String> rawMetadata);
}
