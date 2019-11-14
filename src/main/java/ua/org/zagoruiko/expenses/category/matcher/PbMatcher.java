package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.SystemTag;
import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.resolver.ResolverFromString;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PbMatcher implements Matcher {
    private final Collection<? extends ResolverFromString<Set<Tag>>> tagResolvers;

    public PbMatcher(Collection<? extends ResolverFromString<Set<Tag>>> tagResolvers) {
        this.tagResolvers = tagResolvers;
    }


    private Set<Tag> tagsFromOperationDesc(String operationDesc) {
        Set<Tag> tags = new HashSet<>();
        tags.add(SystemTag.SRC_PRIVAT_BANK.getTag());
        return this.tagResolvers.stream().flatMap(r -> r.resolve(operationDesc))
                .reduce(tags, (accTags, newTags) -> {accTags.addAll(newTags); return accTags;});
    }

    @Override
    public Set<Tag> metadata(Map<String, String> rawMetadata) {
        return tagsFromOperationDesc(rawMetadata.get("operation"));
    }
}
