package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;
import java.util.function.Predicate;

public class AbstractContainsResolver<O> extends AbstractPredicateMapResolver<O> {
    public AbstractContainsResolver(Map<String, O> mapping) {
        super(mapping, (input, key) -> input != null && input.contains(key));
    }
}
