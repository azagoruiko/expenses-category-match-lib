package ua.org.zagoruiko.expenses.category.resolver;

public abstract class AbstractStringPatternResolver<O> implements ResolverFromString<O> {
    protected String pattern;

    public AbstractStringPatternResolver(String pattern) {
        this.pattern = pattern;
    }
}
