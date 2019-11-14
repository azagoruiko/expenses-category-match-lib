package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractByMapResolver<O> implements ResolverFromString<O> {
    protected final Map<String, O> mapping;

    public AbstractByMapResolver(Map<String, O> mapping) {
        this.mapping = mapping;
    }

    protected abstract Stream<O> resolveByMap(String input);

    @Override
    public Stream<O> resolve(String input) {
        return resolveByMap(input);
    }
}
