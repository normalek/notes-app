package com.marhaba.notes_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NoteSummaryDTO {
    private String title;
    private LocalDateTime createdDate;
}
