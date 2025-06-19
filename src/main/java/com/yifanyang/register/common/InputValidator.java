package com.yifanyang.register.common;

import com.yifanyang.register.discount.DiscountType;
import com.yifanyang.register.exception.ErrorMessage;
import com.yifanyang.register.exception.InvalidEntryException;
import com.yifanyang.register.item.ItemUnit;

import java.util.List;

public class InputValidator {

    public void validateEntrySize(List<String> fields, int size) throws InvalidEntryException {
        if (fields.size() !=  size) {
            throw new InvalidEntryException(ErrorMessage.INVALID_ENTRY_SIZE);
        }
    }

    public int validateZero(String field, FieldName name) throws InvalidEntryException {
        int output;
        try {
            output = Integer.valueOf(field);
            if (output != 0) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            throw new InvalidEntryException(ErrorMessage.INVALID_FIELD_ZERO, name);
        }
        return output;
    }

    public int validatePosInt(String field, FieldName name) throws InvalidEntryException {
        int output;
        try {
            output = Integer.valueOf(field);
            if (output <= 0) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            throw new InvalidEntryException(ErrorMessage.INVALID_FIELD_POSINT, name);
        }
        return output;
    }

    public double validatePosDbl(String field, FieldName name) throws InvalidEntryException {
        double output;
        try {
            output = Double.valueOf(field);
            if (output <= 0) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            throw new InvalidEntryException(ErrorMessage.INVALID_FIELD_POSDBL, name);
        }
        return output;
    }

    public ItemUnit validateItemUnit(String field, FieldName name) throws InvalidEntryException {
        try {
            return ItemUnit.valueOf(field.toUpperCase());
        } catch (Exception e) {
            throw new InvalidEntryException(ErrorMessage.INVALID_FIELD_ITEMUNIT, name);
        }
    }

    public DiscountType validateDiscountType(String field, FieldName name) throws InvalidEntryException {
        try {
            return DiscountType.valueOf(field.toUpperCase());
        } catch (Exception e) {
            throw new InvalidEntryException(ErrorMessage.INVALID_FIELD_DISCOUNTTYPE, name);
        }
    }
}
