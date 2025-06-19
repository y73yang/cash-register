package com.yifanyang.register;

import com.yifanyang.register.common.BillCalculator;
import com.yifanyang.register.common.DataImporter;
import com.yifanyang.register.discount.DiscountSpendAndSave;
import com.yifanyang.register.item.Item;
import com.yifanyang.register.shopping_item.ShoppingItem;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * A Java application that simulates a grocery store cash register with support for item-level
 * and spend-and-save discounts.
 */
@Slf4j
public class CashRegister {

    public static void main(String[] args) {

        // Validate input parameters
        if (args.length != 3) {
            log.error("Usage: java -jar cash-register-*-aio.jar <item_file> <discount_file> <shopping_item_file>");
            System.exit(1);
        }
        for (String arg : args) {
            File f = new File(arg);
            if (!f.exists() || !f.isFile() || !f.canRead()) {
                log.error("Invalid input file path {}", arg);
                System.exit(1);
            }
        }

        DataImporter dataImporter = new DataImporter(args[0], args[1], args[2]);
        // get the items available for sale at the grocery store
        Map<Integer, Item> items = dataImporter.importItem();
        // get the spend-and-save discounts and update the grocery items with item discounts
        List<DiscountSpendAndSave> discountSpendAndSaves = dataImporter.importDiscount(items);
        // get the shopping list items
        Map<Integer, ShoppingItem> shoppingItems = dataImporter.importShoppingItem(items);

        log.info("Calculating the grocery bill...");
        // get the bill after the item discounts but before any spend-and-save discount
        double billAfterItemDiscount = BillCalculator.getBillAfterItemDiscount(items, shoppingItems);
        // get the bill after all the discounts
        BillCalculator.getBillAfterSpendAndSaveDiscount(billAfterItemDiscount, discountSpendAndSaves);
    }

}