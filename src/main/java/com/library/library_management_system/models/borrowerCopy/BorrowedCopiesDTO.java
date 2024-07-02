package com.library.library_management_system.models.borrowerCopy;

import com.library.library_management_system.entity.enumeration.CopyState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BorrowedCopiesDTO {

    private Long borrowerId;
    private String borrowerFirstName;
    private String borrowerLastName;
    private String phoneNumber;
    private List<CopyDetails> borrowedCopies;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CopyDetails {
        private Long copyId;
        private String bookTitle;
        private CopyState copyState;
        private Date borrowDate;
        private Date supposedReturnDate;
        private Date returnDate;
    }
}
