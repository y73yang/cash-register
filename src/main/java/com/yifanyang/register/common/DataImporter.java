package com.yifanyang.register.common;

import com.yifanyang.register.discount.Discount;
import com.yifanyang.register.discount.DiscountBulk;
import com.yifanyang.register.discount.DiscountDollarsOff;
import com.yifanyang.register.discount.DiscountSpendAndSave;
import com.yifanyang.register.exception.ErrorMessage;
import com.yifanyang.register.exception.InvalidEntryException;
import com.yifanyang.register.item.Item;
import com.yifanyang.register.item.ItemUnit;
import com.yifanyang.register.shopping_item.ShoppingItem;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DataImporter {

    final static String DELIMITER = ",";
    final static int ITEM_FILE_ENTRY_SIZE = 4;
    final static int DISCOUNT_FILE_ENTRY_SIZE = 5;
    final static int SHOPPING_ITEM_FILE_ENTRY_SIZE = 2;


    String itemFile;
    String discountFile;
    String shoppingItemFile;
    InputValidator inputValidator;

    public DataImporter(String itemFile, String discountFile, String shoppingFile) {
        this.itemFile = itemFile;
        this.discountFile = discountFile;
        this.shoppingItemFile = shoppingFile;
        this.inputValidator = new InputValidator();
    }


    /**
     * @return The items available for sale at the grocery store
     * Note that the older item in the map will be overwritten by the new one with the same key
     */
    public Map<Integer, Item> importItem() {

        log.info("Importing items...");

        List<String> lines = getFileEntries(itemFile);

        Map<Integer, Item> items = new HashMap<>();

        int lineCount = 0;
        for (String line : lines) {
            lineCount++;
            List<String> fields = Arrays.asList(line.split(DELIMITER, -1));
            try {
                inputValidator.validateEntrySize(fields, ITEM_FILE_ENTRY_SIZE);
                Item item = new Item(fields, inputValidator);
                items.put(item.getId(), item);
            } catch (InvalidEntryException e) {
                log.error("Failed to import items - Line {}: {}", lineCount, e.getMessage());
            }
        }

        log.info("Number of items successfully imported: {}", items.size());

        return items;
    }


    /**
     * @param items The items available for sale at the grocery store
     * @return The discount list that contains all the spend-and-save discounts.
     * Meanwhile, all item discounts are added into the input items map
     * Note that a per-item item discount will be ignored if the corresponding item in the map is per-kg
     * Note that an item discount will be ignored if the corresponding item is not in the map
     */
    public List<DiscountSpendAndSave> importDiscount(Map<Integer, Item> items) {

        log.info("Importing discounts...");

        List<String> lines = getFileEntries(discountFile);

        List<DiscountSpendAndSave> discountSpendAndSaves = new ArrayList<>();

        int lineCount = 0;
        int successCount = 0;

        for (String line : lines) {
            lineCount++;
            List<String> fields = Arrays.asList(line.split(DELIMITER, -1));
            try {
                inputValidator.validateEntrySize(fields, DISCOUNT_FILE_ENTRY_SIZE);
                Discount discount = null;
                switch (inputValidator.validateDiscountType(fields.get(1), FieldName.TYPE)) {
                    case DOLLARS_OFF:
                        discount = new DiscountDollarsOff(fields, inputValidator);
                        addItemDiscount(items.get(discount.getItemId()), discount);
                        break;
                    case BULK_DISCOUNT:
                        discount = new DiscountBulk(fields, inputValidator);
                        addItemDiscount(items.get(discount.getItemId()), discount);
                        break;
                    case SPEND_AND_SAVE_DISCOUNT:
                        discountSpendAndSaves.add(new DiscountSpendAndSave(fields, inputValidator));
                        break;
                    default:
                        throw new InvalidEntryException(ErrorMessage.DISCOUNTTYPE_NOT_SUPPORT);
                }
                successCount++;
            } catch (InvalidEntryException e) {
                log.error("Failed to import discounts - Line {}: {}", lineCount, e.getMessage());
            }
        }
        log.info("Number of discounts successfully imported: {}", successCount);
        return discountSpendAndSaves;
    }

    private void addItemDiscount(Item item, Discount discount) throws InvalidEntryException {
        if (item == null) {
            throw new InvalidEntryException(ErrorMessage.DISCOUNT_NOT_MATCH_ITEM);
        } else if (item.getItemUnit() == ItemUnit.KG) {
            throw new InvalidEntryException(ErrorMessage.ITEMUNIT_DISCOUNTTPE_NOT_MATCH);
        } else {
            item.addDiscount(discount);
        }
    }

    /**
     * @param items The items available for sale at the grocery store
     * @return The shopping items map that represents the list of groceries to purchase
     * Note that a shopping item will be ignored if the corresponding item is not in the map
     * Note that a shopping item with non-int quantity will be ignored
     *              if the corresponding item in the map has "each" as its unit
     * Note that quantity will be added up for shopping items with the same key in the map
     */
    public Map<Integer, ShoppingItem> importShoppingItem(Map<Integer, Item> items) {

        log.info("Importing shopping items...");

        List<String> lines = getFileEntries(shoppingItemFile);

        Map<Integer, ShoppingItem> shoppingItems = new HashMap<>();

        int lineCount = 0;
        for (String line : lines) {
            lineCount++;
            List<String> fields = Arrays.asList(line.split(DELIMITER, -1));
            try {
                inputValidator.validateEntrySize(fields, SHOPPING_ITEM_FILE_ENTRY_SIZE);
                ShoppingItem shoppingItem = new ShoppingItem(fields, inputValidator);
                addShoppingItem(items.get(shoppingItem.getItemId()), shoppingItem, shoppingItems);
            } catch (InvalidEntryException e) {
                log.error("Failed to import shopping items - Line {}: {}", lineCount, e.getMessage());
            }
        }

        log.info("Number of shopping items successfully imported: {}", shoppingItems.size());

        return shoppingItems;
    }

    private void addShoppingItem(Item item, ShoppingItem shoppingItem, Map<Integer,ShoppingItem> shoppingItems)
            throws InvalidEntryException {
        if (item == null) {
            throw new InvalidEntryException(ErrorMessage.SHOPPING_ITEM_NOT_MATCH_ITEM);
        } else if (item.getItemUnit() == ItemUnit.EACH && shoppingItem.getQuantity() % 1 != 0) {
            throw new InvalidEntryException(ErrorMessage.ITEMUNIT_QUANTITY_NOT_MATCH);
        } else if(shoppingItems.containsKey(shoppingItem.getItemId())) {
            shoppingItems.get(shoppingItem.getItemId()).addQuantity(shoppingItem.getQuantity());
        } else {
            shoppingItems.put(shoppingItem.getItemId(), shoppingItem);
        }
    }

    public List<String> getFileEntries(String inputFilePath) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(inputFilePath));
        } catch (IOException e) {
            log.error("Failed to read input file.");
            System.exit(1);
        }

        if (!lines.isEmpty()) {
            lines.remove(0);
        }
        return lines;
    }

}
