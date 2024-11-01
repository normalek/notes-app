package com.marhaba.notes_app.dto.exception;

import com.marhaba.notes_app.dto.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BusinessException extends RuntimeException {
  private ErrorCode errorCode;
  private HttpStatus httpStatus;
}
