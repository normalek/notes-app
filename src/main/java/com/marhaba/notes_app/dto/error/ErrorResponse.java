package com.marhaba.notes_app.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private HttpStatus httpStatus;
    private String message;
}
