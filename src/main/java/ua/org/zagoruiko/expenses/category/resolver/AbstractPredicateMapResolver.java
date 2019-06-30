package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public abstract class AbstractPredicateMapResolver<O> extends AbstractByMapResolver<O> {
    protected SerializableBiPredicate<String, String> predicate;

    @Override
    protected Stream<O> resolveByMap(String input) {
        return this.mapping.entrySet().stream()
                .filter(entry -> this.predicate.test(input, entry.getKey())).map(entry -> entry.getValue());
    }

    public AbstractPredicateMapResolver(Map<String, O> mapping, SerializableBiPredicate<String, String> predicate) {
        super(mapping);
        this.predicate = predicate;
    }
}
