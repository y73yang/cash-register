package com.yifanyang.register.discount;

public enum DiscountType {

    DOLLARS_OFF("dollars_off"),
    BULK_DISCOUNT("bulk_discount"),
    SPEND_AND_SAVE_DISCOUNT("spend_and_save_discount");

    String discountType;

    DiscountType(String discountType) {
        this.discountType = discountType;
    }

    @Override
    public String toString() {
        return discountType;
    }
}
