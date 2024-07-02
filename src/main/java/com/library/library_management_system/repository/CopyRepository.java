package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Copy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopyRepository extends JpaRepository<Copy,Long> {

    @Query(
            value = "SELECT * " +
                    "FROM copy " +
                    "WHERE copy.book_id IN ( " +
                    "SELECT book_id " +
                    "FROM book " +
                    "WHERE book.title = ?1)",
            nativeQuery = true
    )
    List<Copy> findAllByBookTitle(String bookIsbn);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE copy " +
                    "SET state = ?2, available = ?3 " +
                    "WHERE copy_id = ?1",
            nativeQuery = true
    )
    void updateCopy(Long copyId, String state, boolean available);

    @Query(
            value = "SELECT * " +
                    "FROM copy " +
                    "WHERE available = true and book_id IN ( " +
                    "SELECT book_id " +
                    "FROM book " +
                    "WHERE title like ?1)",
            nativeQuery = true
    )
    List<Copy> findAvailableByBookTitle(String bookTitle);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE copy " +
                    "SET available = ?2 " +
                    "WHERE copy_id = ?1",
            nativeQuery = true
    )
    void updateCopyAvailability(Long copyId,boolean available);
}
