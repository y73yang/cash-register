package com.yifanyang.register.shopping_item;

import com.yifanyang.register.common.FieldName;
import com.yifanyang.register.common.InputValidator;
import com.yifanyang.register.exception.InvalidEntryException;
import lombok.Getter;

import java.util.List;

@Getter
public class ShoppingItem {

    private int itemId;
    private double quantity;

    public ShoppingItem(List<String> fields, InputValidator inputValidator) throws InvalidEntryException {
        this.itemId = inputValidator.validatePosInt(fields.get(0), FieldName.ITEM_ID);
        this.quantity = inputValidator.validatePosDbl(fields.get(1), FieldName.QUANTITY);
    }

    public void addQuantity(double quantity) {
        this.quantity+=quantity;
    }
}
