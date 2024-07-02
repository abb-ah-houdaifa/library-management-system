package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Borrower;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Long> {
    List<Borrower> findByFirstname(String firstname);

    List<Borrower> findByFirstnameAndLastname(String firstname, String lastname);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE borrower " +
                    "SET blocked = true and block_date = ?2 " +
                    "WHERE borrower_id = ?1",
            nativeQuery = true
    )
    void updateBlockBorrower(Long borrowerId, Date today);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE borrower " +
                    "SET blocked = false and block_date = NULL " +
                    "WHERE borrower_id = ?1",
            nativeQuery = true
    )
    void updateUnblockBorrower(Long borrowerId);
}
