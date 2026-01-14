package com.example.gujeuck_server.infrastructure.excel.exception;

import com.example.gujeuck_server.global.error.exception.ErrorCode;
import com.example.gujeuck_server.global.error.exception.GujeukException;

public class ExcelGenerationException extends GujeukException {
    public static final GujeukException EXCEPTION = new ExcelGenerationException();

    private ExcelGenerationException() {
        super(ErrorCode.EXCEL_GENERATION_FAILED);
    }
}
