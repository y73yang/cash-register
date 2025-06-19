package com.yifanyang.register.item;

import com.yifanyang.register.common.FieldName;
import com.yifanyang.register.common.InputValidator;
import com.yifanyang.register.discount.Discount;
import com.yifanyang.register.shopping_item.ShoppingItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Item {

    private int id;
    private String name;
    private double price;
    private ItemUnit itemUnit;
    private List<Discount> discounts;

    public Item(List<String> fields, InputValidator inputValidator) {
        this.id = inputValidator.validatePosInt(fields.get(0), FieldName.ITEM_ID);
        this.name = fields.get(1);
        this.price = inputValidator.validatePosDbl(fields.get(2), FieldName.PRICE);
        this.itemUnit = inputValidator.validateItemUnit(fields.get(3), FieldName.UNIT);
        this.discounts = new ArrayList<>();
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    /**
     * @param shoppingListItem The shopping list item
     * @return The price after the item discounts for a shopping item
     * Note that if the item is qualified for multiple item discounts,
     *      only the one with the highest discount rate will be used.
     *      (i.e. item discounts for the same item will not stack)
     */
    public double getPriceAfterItemDiscount(ShoppingItem shoppingListItem) {

        double priceBeforeItemDiscount = price * shoppingListItem.getQuantity();

        double priceAfterItemDiscount = discounts.stream()
                .map(disc -> disc.applyDiscount(price, shoppingListItem))
                .min(Double::compare)
                .orElse(priceBeforeItemDiscount);

        log.info("Id: {}\titem: {}\tBefore: ${}\tAfter: ${}", id, name,
                String.format("%.2f", priceBeforeItemDiscount), String.format("%.2f", priceAfterItemDiscount));

        return priceAfterItemDiscount;
    }
}
