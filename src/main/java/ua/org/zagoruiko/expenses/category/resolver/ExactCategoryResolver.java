package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;

public class ExactCategoryResolver extends AbstractExactMapResolver<String> {
    public ExactCategoryResolver(Map<String, String> mapping) {
        super(mapping);
    }
}
