package com.library.library_management_system.service;

import com.library.library_management_system.entity.Borrower;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.exception.OperationNotAllowedException;
import com.library.library_management_system.repository.BorrowerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final Validator validator;

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Borrower getBorrowerById(Long borrowerId) {
        return borrowerRepository.findById(borrowerId).orElseThrow(
                () -> new NotFoundException("Borrower with ID : " + borrowerId + " Not Found")
        );
    }

    public List<Borrower> findBorrowerByFirstnameAndLastname(String firstname, String lastname) {
        if(lastname == null)
            return borrowerRepository.findByFirstname(firstname);
        return borrowerRepository.findByFirstnameAndLastname(firstname,lastname);
    }

    public Borrower saveBorrower(Borrower borrower) {
        validateBorrowerAttributes(borrower);

        borrower.setBlocked(false);

        return borrowerRepository.save(borrower);
    }

    private void validateBorrowerAttributes(Borrower borrower) {
        Set<ConstraintViolation<Borrower>> violations = validator.validate(borrower);

        if(!violations.isEmpty()){
            StringBuilder violationMessage = new StringBuilder();
            for(ConstraintViolation<Borrower> violation : violations){
                violationMessage.append(violation.getMessage()).append(", ");
            }

            throw new ConstraintViolationException("Error occurred : " + violationMessage, violations);
        }
    }

    public Borrower blockBorrower(Long borrowerId) {
        Borrower blockedBorrower = this.getBorrowerById(borrowerId);
        if(blockedBorrower.isBlocked())
            throw new OperationNotAllowedException("Borrower with ID: " + borrowerId + " is already blocked");

        Date today = Calendar.getInstance().getTime();
        blockedBorrower.setBlocked(true);
        blockedBorrower.setBlockDate(today);

        borrowerRepository.updateBlockBorrower(
                borrowerId,
                today
        );

        return blockedBorrower;
    }

    public Borrower unblockBorrower(Long borrowerId) {
        Borrower unblockedBorrower = this.getBorrowerById(borrowerId);
        if(!unblockedBorrower.isBlocked()){
            throw new OperationNotAllowedException("Borrower with ID: " + borrowerId + " is not blocked");
        }

        unblockedBorrower.setBlocked(false);
        unblockedBorrower.setBlockDate(null);

        borrowerRepository.updateUnblockBorrower(
                borrowerId
        );

        return unblockedBorrower;
    }

    public Borrower editBorrower(Long borrowerId, Borrower newBorrowerDetails) {
        Borrower borrower = this.getBorrowerById(borrowerId);

        String firstname = (newBorrowerDetails.getFirstname() == null)? borrower.getFirstname() : newBorrowerDetails.getFirstname();
        String lastname = (newBorrowerDetails.getLastname() == null)? borrower.getLastname() : newBorrowerDetails.getLastname();
        String phoneNumber = (newBorrowerDetails.getPhoneNumber() == null)? borrower.getPhoneNumber() : newBorrowerDetails.getPhoneNumber();

        borrower.setFirstname(firstname);
        borrower.setLastname(lastname);
        borrower.setPhoneNumber(phoneNumber);

        return borrowerRepository.save(borrower);
    }
}
