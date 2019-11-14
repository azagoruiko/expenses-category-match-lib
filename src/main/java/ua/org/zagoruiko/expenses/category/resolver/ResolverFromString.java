package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;
import java.util.stream.Stream;

public interface ResolverFromString<O> extends Resolver<String, O>, Serializable {
}
