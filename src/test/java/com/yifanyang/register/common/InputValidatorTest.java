package com.yifanyang.register.common;

import com.yifanyang.register.discount.DiscountType;
import com.yifanyang.register.exception.ErrorMessage;
import com.yifanyang.register.exception.InvalidEntryException;
import com.yifanyang.register.item.ItemUnit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class InputValidatorTest {

    private InputValidator inputValidator = new InputValidator();

    @Test
    public void validateEntrySize() {
        try {
            inputValidator.validateEntrySize(Collections.nCopies(5, "foo"), 10);
            fail();
        } catch (InvalidEntryException e) {
            assertEquals(e.getMessage(), ErrorMessage.INVALID_ENTRY_SIZE.toString());
        }
    }

    @Test
    public void validateZero() {
        inputValidator.validateZero("0", FieldName.ITEM_ID);
        try {
            inputValidator.validateZero("1", FieldName.ITEM_ID);
            fail();
        } catch (InvalidEntryException e) {
            assertEquals(e.getMessage(), String.format(ErrorMessage.INVALID_FIELD_ZERO.toString(),FieldName.ITEM_ID));
        }
    }

    @Test
    public void validatePosInt() {

        assertEquals(inputValidator.validatePosInt("1", FieldName.ITEM_ID), 1);

        List<String> invalidPosInts = Arrays.asList("", "0", "0.1", "string");
        for (String invalidPosInt : invalidPosInts) {
            try {
                inputValidator.validatePosInt(invalidPosInt, FieldName.ITEM_ID);
                fail();
            } catch (InvalidEntryException e) {
                assertEquals(
                        e.getMessage(), String.format(ErrorMessage.INVALID_FIELD_POSINT.toString(), FieldName.ITEM_ID));
            }
        }
    }

    @Test
    public void validatePosDbl() {

        assertEquals(inputValidator.validatePosDbl("1", FieldName.PRICE), 1.0, 0);
        assertEquals(inputValidator.validatePosDbl("0.1", FieldName.PRICE), 0.1, 0);

        List<String> invalidPosDbls = Arrays.asList("0", "-0.1", "string");
        for (String invalidPosDbl : invalidPosDbls) {
            try {
                inputValidator.validatePosDbl(invalidPosDbl, FieldName.PRICE);
                fail();
            } catch (InvalidEntryException e) {
                assertEquals(
                        e.getMessage(), String.format(ErrorMessage.INVALID_FIELD_POSDBL.toString(), FieldName.PRICE));
            }
        }
    }

    @Test
    public void validateItemUnit() {
        assertEquals(inputValidator.validateItemUnit("each", FieldName.UNIT), ItemUnit.EACH);
        assertEquals(inputValidator.validateItemUnit("KG", FieldName.UNIT), ItemUnit.KG);

        try {
            inputValidator.validateItemUnit("ml", FieldName.UNIT);
            fail();
        } catch (InvalidEntryException e) {
            assertEquals(
                    e.getMessage(), String.format(ErrorMessage.INVALID_FIELD_ITEMUNIT.toString(), FieldName.UNIT));
        }
    }

    @Test
    public void validateDiscountType() {

        assertEquals(inputValidator.validateDiscountType("dollars_off", FieldName.TYPE), DiscountType.DOLLARS_OFF);
        assertEquals(inputValidator.validateDiscountType("spend_and_save_discount", FieldName.TYPE),
                DiscountType.SPEND_AND_SAVE_DISCOUNT);

        try {
            inputValidator.validateDiscountType("DOLLARS OFF", FieldName.TYPE);
            fail();
        } catch (InvalidEntryException e) {
            assertEquals(
                    e.getMessage(), String.format(ErrorMessage.INVALID_FIELD_DISCOUNTTYPE.toString(), FieldName.TYPE));
        }
    }

}