package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;
import java.util.function.Predicate;

public class AbstractStartsWithResolver<O> extends AbstractPredicateMapResolver<O> {
    public AbstractStartsWithResolver(Map<String, O> mapping) {
        super(mapping, (input, key) -> input != null && input.startsWith(key));
    }
}
