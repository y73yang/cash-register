package com.yifanyang.register.exception;

import com.yifanyang.register.discount.DiscountType;
import com.yifanyang.register.item.ItemUnit;

import java.util.Arrays;

public enum ErrorMessage {
    INVALID_ENTRY_SIZE("Invalid entry size"),
    INVALID_FIELD_ZERO("Invalid entry field \"%s\" - Expect zero"),
    INVALID_FIELD_POSINT("Invalid entry field \"%s\" - Expect positive integer"),
    INVALID_FIELD_POSDBL("Invalid entry field \"%s\" - Expect positive double"),
    INVALID_FIELD_ITEMUNIT("Invalid entry field \"%s\" - Expect " + Arrays.toString(ItemUnit.values())),
    INVALID_FIELD_DISCOUNTTYPE("Invalid entry field \"%s\" - Expect " + Arrays.toString(DiscountType.values())),

    DISCOUNTTYPE_NOT_SUPPORT("Discount type not yet supported"),
    DISCOUNT_NOT_MATCH_ITEM("Discount not match any item"),
    ITEMUNIT_DISCOUNTTPE_NOT_MATCH("Item unit and discount type not match"),

    SHOPPING_ITEM_NOT_MATCH_ITEM("Shopping item not match any item"),
    ITEMUNIT_QUANTITY_NOT_MATCH("Item unit and purchase quantity type not match");

    String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return errorMessage;
    }

}
