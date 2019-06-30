package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Predicate;

public class AbstractContainsResolver<O> extends AbstractPredicateMapResolver<O> {
    private static class ContainsBiPredicate implements SerializableBiPredicate<String, String>, Serializable {
        @Override
        public boolean test(String input, String key) {
            return input != null && input.contains(key);
        }
    }

    public AbstractContainsResolver(Map<String, O> mapping) {
        super(mapping, new ContainsBiPredicate());
    }
}
