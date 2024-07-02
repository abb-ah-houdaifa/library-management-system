package com.library.library_management_system.models.copy;

import com.library.library_management_system.entity.enumeration.CopyState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CopyResponseDTO {
    private Long copyId;
    private String title;
    private String isbn;
    private String editorName;
    private boolean available;
    private CopyState state;
    private Long bookId;
    private Long editorId;
}
