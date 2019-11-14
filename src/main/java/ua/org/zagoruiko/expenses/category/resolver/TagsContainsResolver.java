package ua.org.zagoruiko.expenses.category.resolver;

import ua.org.zagoruiko.expenses.category.model.Tag;

import java.util.Map;
import java.util.Set;

public class TagsContainsResolver extends AbstractContainsResolver<Set<Tag>> {
    public TagsContainsResolver(Map<String, Set<Tag>> mapping) {
        super(mapping);
    }
}
