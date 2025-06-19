package com.yifanyang.register.common;

import com.yifanyang.register.discount.DiscountSpendAndSave;
import com.yifanyang.register.item.Item;
import com.yifanyang.register.shopping_item.ShoppingItem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillCalculatorTest {

    @Test
    public void verifyBillAfterDiscount() {
        DataImporter dataImporter = new DataImporter(
                this.getClass().getResource("/items.csv").getPath(),
                this.getClass().getResource("/discounts.csv").getPath(),
                this.getClass().getResource("/shopping_items.csv").getPath());

        Map<Integer, Item> items = dataImporter.importItem();
        List<DiscountSpendAndSave> discountSpendAndSaves = dataImporter.importDiscount(items);
        Map<Integer, ShoppingItem> shoppingItems = dataImporter.importShoppingItem(items);
        double billAfterItemDiscount = BillCalculator.getBillAfterItemDiscount(items, shoppingItems);
        assertEquals(billAfterItemDiscount, 222.225, 0);
        double billAfterOverallDiscount = BillCalculator.getBillAfterSpendAndSaveDiscount(
                billAfterItemDiscount, discountSpendAndSaves);
        assertEquals(billAfterOverallDiscount, 162.225, 0);
    }

}