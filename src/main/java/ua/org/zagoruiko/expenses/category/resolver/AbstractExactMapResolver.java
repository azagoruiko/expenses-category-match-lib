package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractExactMapResolver<O> extends AbstractByMapResolver<O> {
    public AbstractExactMapResolver(Map<String, O> mapping) {
        super(mapping);
    }

    @Override
    protected Stream<O> resolveByMap(String input) {
        if (!this.mapping.containsKey(input)) {
            return Stream.of();
        }
        return Stream.of(this.mapping.get(input));
    }
}
