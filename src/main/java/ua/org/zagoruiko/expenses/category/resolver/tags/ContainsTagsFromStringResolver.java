package ua.org.zagoruiko.expenses.category.resolver.tags;

import ua.org.zagoruiko.expenses.category.model.Tag;

import java.io.Serializable;
import java.util.Set;

public class ContainsTagsFromStringResolver extends AbstractTagsFromStringPredicateResolver implements Serializable {
    public ContainsTagsFromStringResolver(String pattern, Set<Tag> resolved) {
        super(pattern,
                (s1, s2) -> s1.toUpperCase().contains(s2.toUpperCase()), resolved);
    }
}
