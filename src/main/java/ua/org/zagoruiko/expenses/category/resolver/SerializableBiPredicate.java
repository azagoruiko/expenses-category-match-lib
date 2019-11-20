package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;
import java.util.function.BiPredicate;

public interface SerializableBiPredicate<V1, V2> extends BiPredicate<V1, V2>, Serializable {
}
