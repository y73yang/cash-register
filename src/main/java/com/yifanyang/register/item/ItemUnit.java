package com.yifanyang.register.item;

public enum ItemUnit {
    EACH("each"),
    KG("kg");

    String unit;

    ItemUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return unit;
    }

}