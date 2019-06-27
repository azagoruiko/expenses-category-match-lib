package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.Category;
import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;
import ua.org.zagoruiko.expenses.category.resolver.Resolver;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PbMatcher implements Matcher, Serializable {
    private final Collection<? extends Resolver<String>> categoryResolvers;
    private final Collection<? extends Resolver<Set<Tag>>> tagResolvers;

    public PbMatcher(Collection<? extends Resolver<String>> categoryResolvers,
                      Collection<? extends Resolver<Set<Tag>>> tagResolvers) {
        this.categoryResolvers = categoryResolvers;
        this.tagResolvers = tagResolvers;
    }

    private String categoryFromOperationDesc(String operationDesc) {
        return this.categoryResolvers.stream()
                .flatMap(r -> r.resolve(operationDesc)).findAny().orElse(Category.UNKNOWN.getFriendlyName());
    }

    private Set<Tag> tagsFromOperationDesc(String operationDesc) {
        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.SRC_PRIVAT_BANK);
        return this.tagResolvers.stream().flatMap(r -> r.resolve(operationDesc))
                .reduce(tags, (accTags, newTags) -> {accTags.addAll(newTags); return accTags;});
    }

    @Override
    public TransactionMetadata metadata(Map<String, String> rawMetadata) {
        String category = categoryFromOperationDesc(rawMetadata.get("operation"));
        if (Category.UNKNOWN.getFriendlyName().equals(category)) {
            category = categoryFromOperationDesc(rawMetadata.get("category"));
        }

        Set<Tag> tags = tagsFromOperationDesc(rawMetadata.get("operation"));
        return new TransactionMetadata(category, tags);
    }
}
