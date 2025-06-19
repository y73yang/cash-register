package com.yifanyang.register.discount;

import com.yifanyang.register.common.FieldName;
import com.yifanyang.register.common.InputValidator;
import com.yifanyang.register.shopping_item.ShoppingItem;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Discount {

    private int itemId;
    private DiscountType type;
    private String description;

    public Discount(List<String> fields, InputValidator fieldValidator) {
        this.type = fieldValidator.validateDiscountType(fields.get(1), FieldName.TYPE);
        this.itemId = type == DiscountType.SPEND_AND_SAVE_DISCOUNT ?
                fieldValidator.validateZero(fields.get(0), FieldName.ITEM_ID) :
                fieldValidator.validatePosInt(fields.get(0), FieldName.ITEM_ID);
        this.description = fields.get(2);
    }

    public abstract double applyDiscount(double price, ShoppingItem shoppingListItem);
}
