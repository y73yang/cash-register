package com.yifanyang.register.common;

public enum FieldName {

        ITEM_ID("item_id"),
        NAME("name"),
        PRICE("price"),
        UNIT("Unit"),
        TYPE("Type"),
        DISCRIPTION("description"),
        INTERVAL("interval"),
        DISCOUNT("discount"),
        QUANTITY("quantity");

        String fieldName;

        FieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return fieldName;
        }

}
