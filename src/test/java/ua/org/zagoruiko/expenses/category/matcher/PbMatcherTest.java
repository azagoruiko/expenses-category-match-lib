package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PbMatcherTest {

    @org.junit.Test
    public void metadata() {
        Matcher matcher = new PbMatcher();
        Map<String, String> pbRecord = new HashMap<>();
        pbRecord.put("category", "Прочее");
        pbRecord.put("operation", "Предавторизация: WFPTAXI");

        TransactionMetadata metadata = matcher.metadata(pbRecord);
        assertEquals(metadata.getCategory(), "Transport");
    }
}