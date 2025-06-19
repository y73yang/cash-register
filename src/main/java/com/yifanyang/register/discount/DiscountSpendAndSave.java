package com.yifanyang.register.discount;

import com.yifanyang.register.common.FieldName;
import com.yifanyang.register.common.InputValidator;
import com.yifanyang.register.exception.InvalidEntryException;
import com.yifanyang.register.shopping_item.ShoppingItem;

import java.util.List;

public class DiscountSpendAndSave extends Discount {

    private double interval;
    private double discount;

    public DiscountSpendAndSave(List<String> fields, InputValidator fieldValidator) throws InvalidEntryException {

        super(fields, fieldValidator);
        this.interval = fieldValidator.validatePosDbl(fields.get(3), FieldName.INTERVAL);
        this.discount = fieldValidator.validatePosDbl(fields.get(4), FieldName.DISCOUNT);

    }

    /**
     * @param billBeforeSpendAndSaveDiscount The bill after the item discounts but before any spend-and-save discount
     * @param shoppingListItem The shopping list item
     * @return The total price (i.e. the final bill) after the spend-and-save discount
     */
    @Override
    public double applyDiscount(double billBeforeSpendAndSaveDiscount, ShoppingItem shoppingListItem) {
        return (billBeforeSpendAndSaveDiscount >= interval) ?
                (billBeforeSpendAndSaveDiscount - discount) : billBeforeSpendAndSaveDiscount;
    }
}
