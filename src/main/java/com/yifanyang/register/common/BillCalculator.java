package com.yifanyang.register.common;

import com.yifanyang.register.discount.DiscountSpendAndSave;
import com.yifanyang.register.item.Item;
import com.yifanyang.register.shopping_item.ShoppingItem;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class BillCalculator {

    /**
     * @param items The items available for sale at the grocery store
     * @param shoppingItems The shopping list items
     * @return The bill after the item discounts for all shopping items
     */
    public static double getBillAfterItemDiscount(Map<Integer, Item> items, Map<Integer, ShoppingItem> shoppingItems) {
        double billAfterItemDiscount = 0;
        for (int itemId : shoppingItems.keySet()) {
            Item item = items.get(itemId);
            ShoppingItem shoppingItem = shoppingItems.get(itemId);
            billAfterItemDiscount += item.getPriceAfterItemDiscount(shoppingItem);
        }
        log.info("Grocery bill amount after item discount: ${}", String.format("%.2f", billAfterItemDiscount));
        return billAfterItemDiscount;
    }

    /**
     * @param billAfterItemDiscount The bill after the item discounts but before any spend-and-save discount
     * @param discountSpendAndSaves The list of spend-and-save discounts
     * @return The bill after the spend-and-save discounts
     * Note that if the bill is qualified for multiple spend-and-save discounts,
     *      only the one with the highest discount rate will be used.
     *      (i.e. same as item discounts, spend-and-save discounts will not stack)
     */
    public static double getBillAfterSpendAndSaveDiscount(
            double billAfterItemDiscount, List<DiscountSpendAndSave> discountSpendAndSaves) {
        double billAfterSpendAndSaveDiscount = discountSpendAndSaves.stream()
                .map(disc -> disc.applyDiscount(billAfterItemDiscount, null))
                .min(Double::compare)
                .orElse(billAfterItemDiscount);
        log.info("Spend-and-save discount: ${}",
                String.format("%.2f", billAfterItemDiscount - billAfterSpendAndSaveDiscount));
        log.info("Total grocery bill amount: ${}", String.format("%.2f", billAfterSpendAndSaveDiscount));
        return billAfterSpendAndSaveDiscount;
    }

}
