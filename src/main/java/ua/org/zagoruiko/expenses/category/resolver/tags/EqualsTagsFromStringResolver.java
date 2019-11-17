package ua.org.zagoruiko.expenses.category.resolver.tags;

import ua.org.zagoruiko.expenses.category.model.Tag;

import java.util.Set;

public class EqualsTagsFromStringResolver extends AbstractTagsFromStringPredicateResolver {
    public EqualsTagsFromStringResolver(String pattern, Set<Tag> resolved) {
        super(pattern,
                (s1, s2) -> s1.equals(s2), resolved);
    }
}
