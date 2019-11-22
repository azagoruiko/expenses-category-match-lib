package ua.org.zagoruiko.expenses.category.resolver.tags;

import ua.org.zagoruiko.expenses.category.model.Tag;

import java.io.Serializable;
import java.util.Set;

public class EqualsTagsFromStringResolver extends AbstractTagsFromStringPredicateResolver implements Serializable {
    public static final String ID = "EQUAL";
    public EqualsTagsFromStringResolver(String pattern, Set<Tag> resolved) {
        super(pattern,
                (s1, s2) -> s1.equals(s2), resolved);
    }
}
