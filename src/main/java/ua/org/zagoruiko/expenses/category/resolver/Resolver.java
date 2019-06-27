package ua.org.zagoruiko.expenses.category.resolver;

import java.util.stream.Stream;

public interface Resolver<O> {
    Stream<O> resolve(String input);
}
