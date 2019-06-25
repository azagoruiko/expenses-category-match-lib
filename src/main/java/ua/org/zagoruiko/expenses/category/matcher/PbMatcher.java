package ua.org.zagoruiko.expenses.category.matcher;

import ua.org.zagoruiko.expenses.category.model.TransactionMetadata;

import java.util.HashMap;
import java.util.Map;

public class PbMatcher implements Matcher {
    private final Map<String, String> categoryMatch = new HashMap<>();
    private final Map<String, String> exactMatch = new HashMap<>();

    private PbMatcher() {
        this.categoryMatch.put("Продукты питания", "Food and Drinks");
        this.categoryMatch.put("Кафе, бары, рестораны", "Cafe and Restaurants");
        this.categoryMatch.put("Развлечения", "Entertainment");
        this.categoryMatch.put("Коммунальные услуги", "Utilities");
        this.categoryMatch.put("Пополнение мобильного", "Utilities");
    }

    @Override
    public TransactionMetadata metadata(Map<String, String> rawMetadata) {
//        if ("Прочее".equals(category)) {
//            if (operation.contains("WFPTAXI")) {
//                return "Transport";
//            }
//        }
//
//        return "Unknown";
        return null;
    }
}
