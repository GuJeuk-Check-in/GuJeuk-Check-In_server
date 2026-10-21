package com.example.burserker.gujeuk.infrastructure.excel.exception;

import com.example.burserker.gujeuk.global.error.exception.ErrorCode;
import com.example.burserker.gujeuk.global.error.exception.GujeukException;

public class ExcelGenerationException extends GujeukException {
    public static final GujeukException EXCEPTION = new ExcelGenerationException();

    private ExcelGenerationException() {
        super(ErrorCode.EXCEL_GENERATION_FAILED);
    }
}
