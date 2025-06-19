package com.yifanyang.register.exception;

import com.yifanyang.register.common.FieldName;

public class InvalidEntryException extends RuntimeException {

    public InvalidEntryException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidEntryException(ErrorMessage errorMessage) {
        super(errorMessage.toString());
    }

    public InvalidEntryException(ErrorMessage errorMessage, FieldName name) {
        super(String.format(errorMessage.toString(), name.toString()));
    }

    public InvalidEntryException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
