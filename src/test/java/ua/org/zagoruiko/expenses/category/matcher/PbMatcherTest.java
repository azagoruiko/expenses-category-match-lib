package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.SystemTag;
import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.resolver.TagsContainsResolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class PbMatcherTest {

    private final Map<String, String> categoryMatch = new HashMap<>();
    private final Map<String, String> categoryContains = new HashMap<>();
    private final Map<String, Set<Tag>> tagContains = new HashMap<>();

    @org.junit.Test
    public void metadata() {
        this.categoryMatch.put("Продукты питания", "Food and Drinks");
        this.categoryMatch.put("Кафе, бары, рестораны", "Cafe and Restaurants");
        this.categoryMatch.put("Развлечения", "Entertainment");
        this.categoryMatch.put("Коммунальные услуги", "Utilities");
        this.categoryMatch.put("Пополнение мобильного", "Utilities");

        this.categoryContains.put("WFPTAXI", "Transport");
        this.categoryContains.put("fozzy", "Food and Drinks");

        this.tagContains.put("WFPTAXI", new HashSet<>(Arrays.asList(new Tag[] {SystemTag.TAG_TAXI.getTag()})));
        this.tagContains.put("fozzy", new HashSet<>(Arrays.asList(new Tag[] {
                SystemTag.TAG_FOZZY.getTag(),
                SystemTag.TAG_SUPERMARKET.getTag(),
                SystemTag.TAG_HAS_DRINKS.getTag()
        })));

        TagsContainsResolver tagsContainsResolver = new TagsContainsResolver(this.tagContains);

        Matcher matcher = new PbMatcher(
                Arrays.asList(tagsContainsResolver));
        Map<String, String> pbRecord = new HashMap<>();
        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: WFPTAXI");

        Set<Tag> tags = matcher.metadata(pbRecord);
        assertTrue(tags.contains(SystemTag.TAG_TAXI.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));

        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: fozzy hahaha");

        tags = matcher.metadata(pbRecord);
        assertTrue(tags.contains(SystemTag.TAG_SUPERMARKET.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_HAS_DRINKS.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_FOZZY.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));

        pbRecord.put("category", "Продукты питания");
        pbRecord.put("operation", "Предавторизация: fozzy hahaha");

        tags = matcher.metadata(pbRecord);
        assertTrue(tags.contains(SystemTag.TAG_SUPERMARKET.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_HAS_DRINKS.getTag()));
        assertTrue(tags.contains(SystemTag.TAG_FOZZY.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));

        pbRecord.put("category", "Продукты питания");
        pbRecord.put("operation", "Some supermarket");

        tags = matcher.metadata(pbRecord);
        assertTrue(!tags.contains(SystemTag.TAG_SUPERMARKET.getTag()));
        assertTrue(!tags.contains(SystemTag.TAG_HAS_DRINKS.getTag()));
        assertTrue(!tags.contains(SystemTag.TAG_FOZZY.getTag()));
        assertTrue(tags.contains(SystemTag.SRC_PRIVAT_BANK.getTag()));
    }
}