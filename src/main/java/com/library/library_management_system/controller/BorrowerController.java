package com.library.library_management_system.controller;

import com.library.library_management_system.models.borrowerCopy.BorrowedCopiesDTO;
import com.library.library_management_system.entity.Borrower;
import com.library.library_management_system.service.BorrowerCopyService;
import com.library.library_management_system.service.BorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;
    private final BorrowerCopyService borrowerCopyService;

    @GetMapping("")
    public ResponseEntity<List<Borrower>> getAllBorrowers(){
        List<Borrower> borrowersList = borrowerService.getAllBorrowers();
        return new ResponseEntity<>(borrowersList,HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Borrower>> getBorrowersByFirstnameAndLastName(
            @RequestParam("first-name") String firstname,
            @RequestParam(value = "last-name", required = false) String lastname
    ){
        List<Borrower> borrowersList = borrowerService.findBorrowerByFirstnameAndLastname(firstname,lastname);
        return new ResponseEntity<>(borrowersList,HttpStatus.FOUND);
    }

    @GetMapping("/{borrowerId}")
    public ResponseEntity<Borrower> getBorrowerById(
            @PathVariable("borrowerId") Long borrowerId
    ){
        Borrower borrower = borrowerService.getBorrowerById(borrowerId);
        return new ResponseEntity<>(borrower,HttpStatus.FOUND);
    }

    @GetMapping("/{borrowerId}/borrow-history")
    public ResponseEntity<BorrowedCopiesDTO> getBorrowerDetailsById(
            @PathVariable("borrowerId") Long borrowerId
    ){
        BorrowedCopiesDTO borrowerHistory = borrowerCopyService.getBorrowerHistory(borrowerId);
        return new ResponseEntity<>(borrowerHistory,HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<Borrower> addBorrower(
            @RequestBody @Valid Borrower borrower
    ){
        Borrower savedBorrower = borrowerService.saveBorrower(borrower);
        return new ResponseEntity<>(savedBorrower,HttpStatus.CREATED);
    }

    @PutMapping("/block/{borrowerId}")
    public ResponseEntity<Borrower> blockBorrower(
            @PathVariable("borrowerId") Long borrowerId
    ){
        Borrower blockedBorrower = borrowerService.blockBorrower(borrowerId);
        return new ResponseEntity<>(blockedBorrower, HttpStatus.OK);
    }

    @PutMapping("/unblock/{borrowerId}")
    public ResponseEntity<Borrower> unblockBorrower(
            @PathVariable("borrowerId") Long borrowerId
    ){
        Borrower unblockedBorrower = borrowerService.unblockBorrower(borrowerId);
        return new ResponseEntity<>(unblockedBorrower, HttpStatus.OK);
    }

    @PutMapping("/edit/{borrowerId}")
    public ResponseEntity<Borrower> editBorrower(
            @PathVariable("borrowerId") Long borrowerId,
            @RequestBody Borrower newBorrowerDetails
    ){
        Borrower editedBorrower = borrowerService.editBorrower(borrowerId, newBorrowerDetails);
        return new ResponseEntity<>(editedBorrower, HttpStatus.ACCEPTED);
    }
}
