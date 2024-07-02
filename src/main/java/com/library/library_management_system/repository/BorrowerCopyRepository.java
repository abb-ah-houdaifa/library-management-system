package com.library.library_management_system.repository;

import com.library.library_management_system.entity.BorrowerCopy;
import com.library.library_management_system.models.borrowerCopy.BorrowedCopiesDTO;
import com.library.library_management_system.models.borrowerCopy.CopyBorrowersDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BorrowerCopyRepository extends JpaRepository<BorrowerCopy,Long> {
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE borrower_copy " +
                    "SET copy_state = ?3, actual_return_date = ?4 " +
                    "WHERE copy_copy_id = ?2 and borrower_borrower_id = ?1",
            nativeQuery = true
    )
    void updateReturningDateAndState(Long borrowerId, Long copyId, String state, Date time);

    //Constructor using jpql
    @Query(
            value = "SELECT new com.library.library_management_system.dto.borrowerCopy.BorrowedCopiesDTO$CopyDetails(" +
                    "bc.copy.copyId, b.title, bc.copyState, bc.borrowingKey.borrowDate,bc.supposedReturnDate, bc.actualReturnDate) " +
                    "FROM BorrowerCopy bc " +
                    "JOIN bc.copy c " +
                    "JOIN c.book b " +
                    "WHERE bc.borrower.borrowerId = :borrowerId"
    )
    List<BorrowedCopiesDTO.CopyDetails> fetchBorrowedCopiesDetails(
            @Param("borrowerId")Long borrowerId
    );

    @Query(
            value = "SELECT new com.library.library_management_system.dto.borrowerCopy.CopyBorrowersDTO$BorrowerDetails(" +
                    "bc.borrower.borrowerId, b.firstname, b.lastname, b.phoneNumber, bc.copyState, " +
                    "bc.borrowingKey.borrowDate,bc.supposedReturnDate, bc.actualReturnDate ) " +
                    "FROM BorrowerCopy bc " +
                    "JOIN bc.borrower b " +
                    "WHERE bc.copy.copyId = :copyId"
    )
    List<CopyBorrowersDTO.BorrowerDetails> fetchAllBorrowersDetailsOfACopy(
            @Param("copyId") Long copyId
    );

    @Query(
            value = "SELECT * " +
                    "FROM borrower_copy " +
                    "WHERE borrower_borrower_id = ?1 and copy_copy_id = ?2",
            nativeQuery = true
    )
    BorrowerCopy fetchMatchedBorrow(Long borrowerId, Long copyId);

}
