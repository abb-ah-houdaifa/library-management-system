package com.library.library_management_system.models.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class BookRequestDTO {
    private String isbn;
    private String title;
    private String summary;
    private List<Long> authorsId;
    private Long editorId;
}
