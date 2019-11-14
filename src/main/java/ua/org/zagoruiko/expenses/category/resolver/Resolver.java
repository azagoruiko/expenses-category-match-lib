package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;
import java.util.stream.Stream;

public interface Resolver<I, O> extends Serializable {
    Stream<O> resolve(I input);
}
