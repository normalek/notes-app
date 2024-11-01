package com.marhaba.notes_app.dto.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOTE_NOT_FOUND("Note with specified id not found in the system");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

}
