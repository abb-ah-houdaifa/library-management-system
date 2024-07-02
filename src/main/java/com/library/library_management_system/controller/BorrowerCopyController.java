package com.library.library_management_system.controller;

import com.library.library_management_system.models.request.BorrowRequest;
import com.library.library_management_system.models.request.ReturnRequest;
import com.library.library_management_system.service.BorrowerCopyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrower/copy")
public class BorrowerCopyController {
    private final BorrowerCopyService borrowerCopyService;

    public BorrowerCopyController(BorrowerCopyService borrowerCopyService) {
        this.borrowerCopyService = borrowerCopyService;
    }

    @PostMapping("/borrow-copy")
    public ResponseEntity<String> borrowBookCopy(
            @RequestBody BorrowRequest borrowRequest
    ){
        borrowerCopyService.borrowCopy(borrowRequest);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/return-copy")
    public ResponseEntity<String> returnBorrowedCopy(
            @RequestBody ReturnRequest ReturnRequest
    ){
        borrowerCopyService.returnBorrowedCopy(ReturnRequest);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}
