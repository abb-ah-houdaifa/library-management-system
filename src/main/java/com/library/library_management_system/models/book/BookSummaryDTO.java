package com.library.library_management_system.models.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSummaryDTO {
    private Long bookId;
    private String isbn;
    private String title;
    private String summary;
    private String editorName;
}
