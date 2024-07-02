package com.library.library_management_system.service;

import com.library.library_management_system.entity.Borrower;
import com.library.library_management_system.entity.BorrowerCopy;
import com.library.library_management_system.entity.Copy;
import com.library.library_management_system.entity.key.BorrowerCopyKey;
import com.library.library_management_system.exception.OperationNotAllowedException;
import com.library.library_management_system.models.borrowerCopy.BorrowedCopiesDTO;
import com.library.library_management_system.models.borrowerCopy.CopyBorrowersDTO;
import com.library.library_management_system.models.request.BorrowRequest;
import com.library.library_management_system.models.request.ReturnRequest;
import com.library.library_management_system.repository.BorrowerCopyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowerCopyService {

    private final BorrowerCopyRepository borrowerCopyRepository;
    private final BorrowerService borrowerService;
    private final CopyService copyService;
    private final int MAX_NUMBER_OF_BOOKS_TO_BORROW_AT_ONE_TIME = 3;

    public void addBorrow(Borrower borrower, Copy copy, int borrowPeriod) {
        Calendar todayCalendar = Calendar.getInstance();

        Calendar ReturnDateCalendar = Calendar.getInstance();
        ReturnDateCalendar.add(
                Calendar.DAY_OF_MONTH,borrowPeriod
        );

        BorrowerCopy borrowing = BorrowerCopy.builder()
                .borrower(borrower)
                .copy(copy)
                .borrowingKey(
                        BorrowerCopyKey.builder()
                                .borrowDate(todayCalendar.getTime())
                                .build()
                )
                .supposedReturnDate(
                        ReturnDateCalendar.getTime()
                )
                .build();

        borrowerCopyRepository.save(borrowing);
    }

    public void updateReturningDateAndState(ReturnRequest returnRequest) {
        Calendar todayCalendar = Calendar.getInstance();

        borrowerCopyRepository.updateReturningDateAndState(
                returnRequest.getBorrowerId(),
                returnRequest.getCopyId(),
                returnRequest.getState().toString(),
                todayCalendar.getTime()
        );
    }

    public void borrowCopy(BorrowRequest borrowRequest) {
        validateBorrowRequest(borrowRequest);

        Copy copy = copyService.findById(borrowRequest.getCopyId());
        if(!copy.isAvailable())
            throw new OperationNotAllowedException("Copy with ID : " + copy.getCopyId() + " is not available");

        Long borrowerId = borrowRequest.getBorrowerId();
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);

        int numberOfBorrowedBooks = this.fetchBorrowedCopiesDetails(borrower.getBorrowerId()).size();
        if(numberOfBorrowedBooks >= MAX_NUMBER_OF_BOOKS_TO_BORROW_AT_ONE_TIME)
            throw new OperationNotAllowedException("Borrower exceeded the limit of borrowed books");

        if(borrower.isBlocked())
            throw new OperationNotAllowedException("Borrower is blocked");

        copyService.updateCopyAvailability(copy, false);
        this.addBorrow(
                borrower,
                copy,
                borrowRequest.getBorrowPeriod()
        );
    }

    public void returnBorrowedCopy(ReturnRequest returnRequest) {
        validateReturnRequest(returnRequest);

        Copy copy = copyService.findById(returnRequest.getCopyId());
        Borrower borrower = borrowerService.getBorrowerById(returnRequest.getBorrowerId());

        BorrowerCopy borrow = this.borrowerCopyRepository.fetchMatchedBorrow(
                borrower.getBorrowerId(),
                copy.getCopyId()
        );
        if(borrow == null)
            throw new OperationNotAllowedException("Borrower never borrowed this copy");

        if(borrow.getActualReturnDate() != null)
            throw new RuntimeException("Copy already have been returned");

        copy.setAvailable(true);
        copy.setState(returnRequest.getState());
        copyService.returnCopy(copy);

        this.updateReturningDateAndState(
                returnRequest
        );
    }

    //get the all the copies borrowed by a person
    public List<BorrowedCopiesDTO.CopyDetails> fetchBorrowedCopiesDetails(Long borrowerId) {
        return borrowerCopyRepository.fetchBorrowedCopiesDetails(borrowerId);
    }

    public BorrowedCopiesDTO getBorrowerHistory(Long borrowerId) {
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);

        List<BorrowedCopiesDTO.CopyDetails> borrowedCopies = this.fetchBorrowedCopiesDetails(borrowerId);

        return BorrowedCopiesDTO.builder()
                .borrowerId(borrowerId)
                .borrowerFirstName(borrower.getFirstname())
                .borrowerLastName(borrower.getLastname())
                .borrowedCopies(borrowedCopies)
                .phoneNumber(borrower.getPhoneNumber())
                .build();
    }

    public void validateReturnRequest(ReturnRequest returnRequest){
        if(returnRequest == null ||
                returnRequest.getBorrowerId() == null ||
                returnRequest.getCopyId() == null
        )
            throw new NullPointerException("The Request is not well structured");
    }

    public void validateBorrowRequest(BorrowRequest borrowRequest){
        if(
                borrowRequest == null ||
                borrowRequest.getBorrowerId() == null ||
                borrowRequest.getCopyId() == null ||
                borrowRequest.getBorrowPeriod() == null
        )
            throw new NullPointerException("The Request is not well structured");
    }

    //get all the borrowers that borrowed a copy
    public CopyBorrowersDTO getBorrowHistory(Long copyId) {
        Copy copy = copyService.findById(copyId);

        List<CopyBorrowersDTO.BorrowerDetails> borrowersList = borrowerCopyRepository.fetchAllBorrowersDetailsOfACopy(copyId);

        return CopyBorrowersDTO.builder()
                .copyId(copyId)
                .isbn(copy.getBook().getIsbn())
                .copyTitle(copy.getBook().getTitle())
                .authors(copy.getBook().getAuthors())
                .borrowers(borrowersList)
                .build();
    }
}
