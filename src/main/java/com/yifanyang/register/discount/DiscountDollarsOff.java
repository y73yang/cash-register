package com.yifanyang.register.discount;

import com.yifanyang.register.common.FieldName;
import com.yifanyang.register.common.InputValidator;
import com.yifanyang.register.exception.InvalidEntryException;
import com.yifanyang.register.shopping_item.ShoppingItem;

import java.util.List;

public class DiscountDollarsOff extends Discount {

    private int interval;
    private double discount;

    public DiscountDollarsOff(List<String> fields, InputValidator fieldValidator) throws InvalidEntryException {

        super(fields, fieldValidator);
        this.interval = fieldValidator.validatePosInt(fields.get(3), FieldName.INTERVAL);
        this.discount = fieldValidator.validatePosDbl(fields.get(4), FieldName.DISCOUNT);
    }


    /**
     * @param unitPrice The item price per unit
     * @param shoppingListItem The shopping list item
     * @return The total price of the shopping list item after the dollar off discount
     */
    @Override
    public double applyDiscount(double unitPrice, ShoppingItem shoppingListItem) {
        int quantity =  (int) shoppingListItem.getQuantity();
        return unitPrice * quantity - (quantity / interval) * discount;
    }
}
