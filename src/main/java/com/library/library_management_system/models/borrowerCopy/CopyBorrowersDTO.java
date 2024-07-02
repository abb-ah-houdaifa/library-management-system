package com.library.library_management_system.models.borrowerCopy;

import com.library.library_management_system.entity.Author;
import com.library.library_management_system.entity.enumeration.CopyState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CopyBorrowersDTO {

    private Long copyId;
    private String isbn;
    private String copyTitle;
    private List<Author> authors;
    private List<BorrowerDetails> borrowers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BorrowerDetails{
        private Long borrowerId;
        private String borrowerFirstname;
        private String borrowerLastname;
        private String phoneNumber;
        private CopyState copyState;
        private Date borrowDate;
        private Date supposedReturnDate;
        private Date returnDate;
    }
}
