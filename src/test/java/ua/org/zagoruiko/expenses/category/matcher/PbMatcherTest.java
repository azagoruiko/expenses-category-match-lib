package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.SystemTag;
import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.resolver.tags.ContainsTagsFromStringResolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PbMatcherTest {

    private final Map<String, Set<Tag>> tagContains = new HashMap<>();

    @org.junit.Test
    public void metadata() {

        this.tagContains.put("WFPTAXI", new HashSet<>(Arrays.asList(new Tag[] {SystemTag.TAG_TAXI.getTag()})));
        this.tagContains.put("fozzy", new HashSet<>(Arrays.asList(new Tag[] {
                SystemTag.TAG_FOZZY.getTag(),
                SystemTag.TAG_SUPERMARKET.getTag(),
                SystemTag.TAG_HAS_DRINKS.getTag()
        })));

        Matcher matcher = new PbMatcher(
                this.tagContains.entrySet().stream()
                        .map(en -> new ContainsTagsFromStringResolver(en.getKey(), en.getValue()))
                        .collect(Collectors.toSet()));
        Map<String, String> pbRecord = new HashMap<>();
        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: WFPTAXI");

        Set<Tag> tags = matcher.match(pbRecord);
        assertTrue(tags.contains(SystemTag.TAG_TAXI.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));

        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: fozzy hahaha");

        tags = matcher.match(pbRecord);
        assertTrue(tags.contains(SystemTag.TAG_SUPERMARKET.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_HAS_DRINKS.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_FOZZY.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));

        pbRecord.put("category", "Продукты питания");
        pbRecord.put("operation", "Предавторизация: fozzy hahaha");

        tags = matcher.match(pbRecord);
        assertTrue(tags.contains(SystemTag.TAG_SUPERMARKET.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_HAS_DRINKS.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_FOZZY.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));

        pbRecord.put("category", "Продукты питания");
        pbRecord.put("operation", "Some supermarket");

        tags = matcher.match(pbRecord);
        assertTrue(!tags.contains(SystemTag.TAG_SUPERMARKET.getTag()));
        assertTrue(!tags.contains(SystemTag.TAG_HAS_DRINKS.getTag()));
        assertTrue(!tags.contains(SystemTag.TAG_FOZZY.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));
    }
}