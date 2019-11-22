package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.SystemTag;
import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.resolver.ResolverFromString;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PbMatcher implements Matcher<Map<String, String>>, Serializable {
    private final Collection<? extends ResolverFromString<Set<Tag>>> tagResolvers;

    public PbMatcher(Collection<? extends ResolverFromString<Set<Tag>>> tagResolvers) {
        this.tagResolvers = tagResolvers;
    }


    private Set<Tag> tagsFromOperationDesc(String operationDesc) {
        Set<Tag> tags = new HashSet<>();
        tags.add(SystemTag.SRC_PRIVAT_BANK.getTag());
        this.tagResolvers.stream().flatMap(r -> r.resolve(operationDesc).stream())
                .forEach(tag -> tags.add(tag));
        return tags;
    }

    @Override
    public Set<Tag> match(Map<String, String> rawMetadata) {
        return tagsFromOperationDesc(rawMetadata.get("operation"));
    }
}
