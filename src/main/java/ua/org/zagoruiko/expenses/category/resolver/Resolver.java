package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;
import java.util.stream.Stream;

public interface Resolver<O> extends Serializable {
    Stream<O> resolve(String input);
}
