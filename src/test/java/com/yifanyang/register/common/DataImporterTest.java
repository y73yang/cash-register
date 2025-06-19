package com.yifanyang.register.common;

import com.yifanyang.register.discount.DiscountSpendAndSave;
import com.yifanyang.register.item.Item;
import com.yifanyang.register.shopping_item.ShoppingItem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataImporterTest {

    DataImporter dataImporter = new DataImporter(
            this.getClass().getResource("/items.csv").getPath(),
            this.getClass().getResource("/discounts.csv").getPath(),
            this.getClass().getResource("/shopping_items.csv").getPath());

    @Test
    public void importItem() {
        Map<Integer, Item> integerItemMap = dataImporter.importItem();
        assertEquals(integerItemMap.size(), 3);
    }

    @Test
    public void importDiscount() {
        Map<Integer, Item> integerItemMap = dataImporter.importItem();
        List<DiscountSpendAndSave> discountSpendAndSaves = dataImporter.importDiscount(integerItemMap);
        assertEquals(integerItemMap.get(1111).getDiscounts().size(), 1);
        assertEquals(integerItemMap.get(1112).getDiscounts().size(), 2);
        assertEquals(integerItemMap.get(5000).getDiscounts().size(), 0);
        assertEquals(discountSpendAndSaves.size(), 2);
    }

    @Test
    public void importShoppingItem() {
        Map<Integer, Item> integerItemMap = dataImporter.importItem();
        Map<Integer, ShoppingItem> integerShoppingItemMap = dataImporter.importShoppingItem(integerItemMap);
        assertEquals(integerShoppingItemMap.size(), 3);
    }
}