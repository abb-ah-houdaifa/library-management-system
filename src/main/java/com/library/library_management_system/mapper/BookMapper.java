package com.library.library_management_system.mapper;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.models.book.BookRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper{

    public Book mapToEntity(BookRequestDTO dto) {
        return Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .summary(dto.getSummary())
                .build();
    }
}
