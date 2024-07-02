package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.models.book.BookSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(
            "SELECT new com.library.library_management_system.dto.book.BookSummaryDTO(" +
                    "b.bookId, b.isbn, b.title, b.summary, e.editorName) " +
                    "FROM Book b " +
                    "JOIN b.editor e " +
                    "JOIN b.authors a " +
                    "WHERE a.authorId = ?1"
    )
    List<BookSummaryDTO> fetchBooksWrittenBy(Long authorId);
}
