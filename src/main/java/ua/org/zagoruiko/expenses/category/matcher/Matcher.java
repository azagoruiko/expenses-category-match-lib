package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.Tag;

import java.io.Serializable;
import java.util.Set;

public interface Matcher<T> extends Serializable {
    Set<Tag> match(T data);
}
