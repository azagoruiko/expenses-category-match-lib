package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.Tag;
import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;
import ua.org.zagoruiko.expenses.category.resolver.CategoryContainsResolver;
import ua.org.zagoruiko.expenses.category.resolver.ExactCategoryResolver;
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

        this.tagContains.put("WFPTAXI", new HashSet<>(Arrays.asList(new Tag[] {Tag.TAG_TAXI})));
        this.tagContains.put("fozzy", new HashSet<>(Arrays.asList(new Tag[] {
                Tag.TAG_FOZZY,
                Tag.TAG_SUPERMARKET,
                Tag.TAG_HAS_DRINKS
        })));

        ExactCategoryResolver categoryResolver = new ExactCategoryResolver(this.categoryMatch);

        CategoryContainsResolver categoryContainsResolver = new CategoryContainsResolver(this.categoryContains);
        TagsContainsResolver tagsContainsResolver = new TagsContainsResolver(this.tagContains);

        Matcher matcher = new PbMatcher(
                Arrays.asList(categoryResolver, categoryContainsResolver),
                Arrays.asList(tagsContainsResolver));
        Map<String, String> pbRecord = new HashMap<>();
        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: WFPTAXI");

        TransactionMetadata metadata = matcher.metadata(pbRecord);
        assertEquals("Transport", metadata.getCategory());
        assertTrue(metadata.getTags().contains(Tag.TAG_TAXI));
        assertTrue(metadata.getTags().contains(Tag.SRC_PRIVAT_BANK));

        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: fozzy hahaha");

        metadata = matcher.metadata(pbRecord);
        assertEquals("Food and Drinks", metadata.getCategory());
        assertTrue(metadata.getTags().contains(Tag.TAG_SUPERMARKET));
        assertTrue(metadata.getTags().contains(Tag.TAG_HAS_DRINKS));
        assertTrue(metadata.getTags().contains(Tag.TAG_FOZZY));
        assertTrue(metadata.getTags().contains(Tag.SRC_PRIVAT_BANK));

        pbRecord.put("category", "Продукты питания");
        pbRecord.put("operation", "Предавторизация: fozzy hahaha");

        metadata = matcher.metadata(pbRecord);
        assertEquals("Food and Drinks", metadata.getCategory());
        assertTrue(metadata.getTags().contains(Tag.TAG_SUPERMARKET));
        assertTrue(metadata.getTags().contains(Tag.TAG_HAS_DRINKS));
        assertTrue(metadata.getTags().contains(Tag.TAG_FOZZY));
        assertTrue(metadata.getTags().contains(Tag.SRC_PRIVAT_BANK));

        pbRecord.put("category", "Продукты питания");
        pbRecord.put("operation", "Some supermarket");

        metadata = matcher.metadata(pbRecord);
        assertEquals("Food and Drinks", metadata.getCategory());
        assertTrue(!metadata.getTags().contains(Tag.TAG_SUPERMARKET));
        assertTrue(!metadata.getTags().contains(Tag.TAG_HAS_DRINKS));
        assertTrue(!metadata.getTags().contains(Tag.TAG_FOZZY));
        assertTrue(metadata.getTags().contains(Tag.SRC_PRIVAT_BANK));
    }
}