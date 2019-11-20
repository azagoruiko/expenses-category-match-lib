package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;

public abstract class AbstractStringPatternResolver<O> implements ResolverFromString<O>, Serializable {
    protected String pattern;

    public AbstractStringPatternResolver(String pattern) {
        this.pattern = pattern;
    }
}
