package ua.org.zagoruiko.expenses.category.resolver;

import java.io.Serializable;

public interface Resolver<I, O> extends Serializable {
    O resolve(I input);
}
