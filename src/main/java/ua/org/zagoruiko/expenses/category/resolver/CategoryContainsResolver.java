package ua.org.zagoruiko.expenses.category.resolver;

import java.util.Map;

public class CategoryContainsResolver extends AbstractContainsResolver<String> {
    public CategoryContainsResolver(Map<String, String> mapping) {
        super(mapping);
    }
}
