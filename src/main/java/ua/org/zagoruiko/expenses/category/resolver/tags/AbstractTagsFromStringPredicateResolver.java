package ua.org.zagoruiko.expenses.category.resolver.tags;

import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.resolver.AbstractStringPredicateResolver;
import ua.org.zagoruiko.expenses.category.resolver.SerializableBiPredicate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AbstractTagsFromStringPredicateResolver extends AbstractStringPredicateResolver<Set<Tag>>
        implements Serializable {
    public AbstractTagsFromStringPredicateResolver(String pattern,
                                                   SerializableBiPredicate<String, String> predicate,
                                                   Set<Tag> resolved) {
        super(pattern, predicate, resolved, new HashSet<>());
    }
}
