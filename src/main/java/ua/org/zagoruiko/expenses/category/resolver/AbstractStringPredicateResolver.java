package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;

public abstract class AbstractStringPredicateResolver<O> extends AbstractStringPatternResolver<O> implements Serializable {
    protected SerializableBiPredicate<String, String> predicate;
    protected O resolved;
    protected O notResolved;

    public AbstractStringPredicateResolver(String pattern,
                                           SerializableBiPredicate<String, String> predicate,
                                           O resolved,
                                           O notResolved) {
        super(pattern);
        this.predicate = predicate;
        this.resolved = resolved;
        this.notResolved = notResolved;
    }

    @Override
    public O resolve(String input) {
        return this.pattern != null
                && input != null
                && this.predicate.test(input, this.pattern)
                ? this.resolved
                : this.notResolved;
    }
}
