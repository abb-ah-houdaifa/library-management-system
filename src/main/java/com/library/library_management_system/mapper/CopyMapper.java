package com.library.library_management_system.mapper;

import com.library.library_management_system.entity.Copy;
import com.library.library_management_system.models.copy.CopyResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CopyMapper{

    public CopyResponseDTO mapToResponseDto(Copy entity) {
        return CopyResponseDTO.builder()
                .copyId(entity.getCopyId())
                .title(entity.getBook().getTitle())
                .isbn(entity.getBook().getIsbn())
                .available(entity.isAvailable())
                .state(entity.getState())
                .bookId(entity.getBook().getBookId())
                .editorName(entity.getBook().getEditor().getEditorName())
                .editorId(entity.getBook().getEditor().getEditorId())
                .build();
    }
}
