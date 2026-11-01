package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.resolver.tags.ContainsTagsFromStringResolver;
import ua.org.zagoruiko.expenses.category.resolver.tags.EqualsTagsFromStringResolver;

import java.util.*;
import java.util.stream.Collectors;

public class GoldenCaseGenerator {

    private static class CategoryTag {
        final String tag;
        final boolean exclusive;
        CategoryTag(String tag, boolean exclusive) { this.tag = tag; this.exclusive = exclusive; }
    }

    private static String pickCategory(List<CategoryTag> catTags, Set<Tag> matchedTags) {
        Set<String> tagNames = matchedTags.stream().map(Tag::getName).collect(Collectors.toSet());
        List<CategoryTag> allMatches = catTags.stream()
                .filter(ct -> tagNames.contains(ct.tag))
                .collect(Collectors.toList());
        if (allMatches.isEmpty()) return "OTHER";
        if (allMatches.size() > 1) {
            allMatches = allMatches.stream().filter(ct -> !ct.exclusive).collect(Collectors.toList());
        }
        return allMatches.stream().findFirst().get().tag;
    }

    private static Tag t(String name) { return new Tag(name, name); }

    private static Map<String, Matcher<String>> buildMatchers(
            Map<String, Map<String, Set<String>>> containsRules,
            Map<String, Map<String, Set<String>>> equalRules,
            Map<String, Tag> systemTags) {

        Map<String, Matcher<String>> matchers = new LinkedHashMap<>();
        for (String source : new String[]{"pb", "alfa", "cs", "spreadsheets"}) {
            Set<Object> resolvers = new LinkedHashSet<>();

            Map<String, Set<String>> srcContains = containsRules.getOrDefault(source, Collections.emptyMap());
            for (Map.Entry<String, Set<String>> e : srcContains.entrySet()) {
                Set<Tag> tags = e.getValue().stream().map(n -> t(n)).collect(Collectors.toSet());
                resolvers.add(new ContainsTagsFromStringResolver(e.getKey(), tags));
            }

            Map<String, Set<String>> srcEquals = equalRules.getOrDefault(source, Collections.emptyMap());
            for (Map.Entry<String, Set<String>> e : srcEquals.entrySet()) {
                Set<Tag> tags = e.getValue().stream().map(n -> t(n)).collect(Collectors.toSet());
                resolvers.add(new EqualsTagsFromStringResolver(e.getKey(), tags));
            }

            Tag sysTag = systemTags.get(source);

            matchers.put(source, new SimplePbMatcher((Collection) resolvers) {
                @Override
                public Set<Tag> match(String operationDescription) {
                    Set<Tag> tags = new HashSet<>();
                    tags.add(sysTag);
                    for (Object r : resolvers) {
                        if (r instanceof ContainsTagsFromStringResolver) {
                            tags.addAll(((ContainsTagsFromStringResolver) r).resolve(operationDescription));
                        } else if (r instanceof EqualsTagsFromStringResolver) {
                            tags.addAll(((EqualsTagsFromStringResolver) r).resolve(operationDescription));
                        }
                    }
                    return tags;
                }
            });
        }
        return matchers;
    }

    public static void main(String[] args) {
        Map<String, Tag> systemTags = new LinkedHashMap<>();
        systemTags.put("pb", t("SRC_PRIVAT_BANK"));
        systemTags.put("alfa", t("SRC_ALFA_BANK"));
        systemTags.put("cs", t("SRC_CS_CZ"));
        systemTags.put("spreadsheets", t("SRC_SPREADSHEETS"));

        Map<String, Map<String, Set<String>>> containsRules = new LinkedHashMap<>();
        Map<String, Map<String, Set<String>>> equalRules = new LinkedHashMap<>();

        Map<String, Set<String>> pbContains = new LinkedHashMap<>();
        pbContains.put("fozzy", new LinkedHashSet<>(Arrays.asList("SUPERMARKET", "FOZZY")));
        pbContains.put("Аптека", new LinkedHashSet<>(Arrays.asList("HEALTHCARE", "PHARMACY")));
        pbContains.put("flight", new LinkedHashSet<>(Arrays.asList("TRAVEL")));
        pbContains.put("Odrex", new LinkedHashSet<>(Arrays.asList("CLINIC")));
        containsRules.put("pb", pbContains);

        Map<String, Set<String>> pbEqual = new LinkedHashMap<>();
        pbEqual.put("Такси UBER", new LinkedHashSet<>(Arrays.asList("TAXI", "TRANSPORT")));
        equalRules.put("pb", pbEqual);

        Map<String, Set<String>> alfaContains = new LinkedHashMap<>();
        alfaContains.put("silpo", new LinkedHashSet<>(Arrays.asList("SUPERMARKET")));
        containsRules.put("alfa", alfaContains);

        Map<String, Set<String>> csContains = new LinkedHashMap<>();
        csContains.put("Albert", new LinkedHashSet<>(Arrays.asList("SUPERMARKET")));
        containsRules.put("cs", csContains);

        Map<String, Set<String>> ssContains = new LinkedHashMap<>();
        ssContains.put("Зарплата", new LinkedHashSet<>(Arrays.asList("INCOME")));
        containsRules.put("spreadsheets", ssContains);

        List<CategoryTag> catTags = Arrays.asList(
                new CategoryTag("SUPERMARKET", false),
                new CategoryTag("TAXI", false),
                new CategoryTag("HEALTHCARE", false),
                new CategoryTag("EAT_OUT", false),
                new CategoryTag("TRAVEL", true),
                new CategoryTag("INCOME", false),
                new CategoryTag("CLINIC", false)
        );

        Map<String, Matcher<String>> matchers = buildMatchers(containsRules, equalRules, systemTags);

        List<Map<String, Object>> cases = new ArrayList<>();

        addCase(cases, matchers, catTags, "pb", "Покупка fozzy Сильпо",
                "CONTAINS substring match (case-insensitive)");
        addCase(cases, matchers, catTags, "pb", "FOZZY.COM PAYMENT",
                "CONTAINS case-insensitive (uppercase input)");
        addCase(cases, matchers, catTags, "pb", "Такси UBER",
                "EQUAL exact match (case-sensitive, matches)");
        addCase(cases, matchers, catTags, "pb", "Такси UBER extra",
                "EQUAL no match (extra text)");
        addCase(cases, matchers, catTags, "pb", "такси uber",
                "EQUAL case-sensitive no match (lowercase input vs uppercase pattern)");
        addCase(cases, matchers, catTags, "pb", "Аптека Odrex",
                "Multiple rules match → union of tags");
        addCase(cases, matchers, catTags, "pb", "flight ticket purchase",
                "TRAVEL exclusive category");
        addCase(cases, matchers, catTags, "pb", "unknown operation xyz",
                "No match → category OTHER");
        addCase(cases, matchers, catTags, "alfa", "Silpo purchase",
                "Alfa system tag SRC_ALFA_BANK");
        addCase(cases, matchers, catTags, "cs", "Albert supermarket",
                "CS system tag SRC_CS_CZ");
        addCase(cases, matchers, catTags, "spreadsheets", "Зарплата за месяц",
                "Spreadsheets system tag SRC_SPREADSHEETS");

        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < cases.size(); i++) {
            Map<String, Object> c = cases.get(i);
            json.append("  {\n");
            json.append("    \"source\": \"").append(c.get("source")).append("\",\n");
            json.append("    \"operation\": \"").append(escapeJson((String) c.get("operation"))).append("\",\n");
            json.append("    \"expected_tags\": [");
            @SuppressWarnings("unchecked")
            List<String> tags = (List<String>) c.get("expected_tags");
            for (int j = 0; j < tags.size(); j++) {
                if (j > 0) json.append(", ");
                json.append("\"").append(tags.get(j)).append("\"");
            }
            json.append("],\n");
            json.append("    \"expected_category\": \"").append(c.get("expected_category")).append("\",\n");
            json.append("    \"description\": \"").append(escapeJson((String) c.get("description"))).append("\"\n");
            json.append("  }");
            if (i < cases.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]\n");
        System.out.print(json.toString());
    }

    private static void addCase(List<Map<String, Object>> cases,
                                 Map<String, Matcher<String>> matchers,
                                 List<CategoryTag> catTags,
                                 String source, String operation, String description) {
        Matcher<String> matcher = matchers.get(source);
        Set<Tag> matched = matcher.match(operation);
        List<String> tags = matched.stream().map(Tag::getName).sorted().collect(Collectors.toList());
        String category = pickCategory(catTags, matched);

        Map<String, Object> c = new LinkedHashMap<>();
        c.put("source", source);
        c.put("operation", operation);
        c.put("expected_tags", tags);
        c.put("expected_category", category);
        c.put("description", description);
        cases.add(c);
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
