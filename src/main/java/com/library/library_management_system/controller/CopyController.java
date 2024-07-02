package com.library.library_management_system.controller;

import com.library.library_management_system.models.borrowerCopy.CopyBorrowersDTO;
import com.library.library_management_system.models.copy.CopyDTO;
import com.library.library_management_system.models.copy.CopyResponseDTO;
import com.library.library_management_system.entity.Copy;
import com.library.library_management_system.mapper.CopyMapper;
import com.library.library_management_system.service.BorrowerCopyService;
import com.library.library_management_system.service.CopyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("copies")
public class CopyController {

    private final CopyService copyService;
    private final BorrowerCopyService borrowerCopyService;
    private final CopyMapper copyMapper;

    public CopyController(CopyService copyService, BorrowerCopyService borrowerCopyService, CopyMapper copyMapper) {
        this.copyService = copyService;
        this.borrowerCopyService = borrowerCopyService;
        this.copyMapper = copyMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<CopyResponseDTO>> getAllCopies(){
        List<Copy> copiesList = copyService.findAllCopies();
        List<CopyResponseDTO> copiesResponseList = copiesList.stream()
                .map(copyMapper::mapToResponseDto)
                .toList();

        return new ResponseEntity<>(copiesResponseList,HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CopyResponseDTO>> getAllCopiesOfBookByTitle(
            @RequestParam(value = "book-title") String bookTitle
    ){
        List<Copy> copiesList = copyService.findAllCopiesByBookTitle(bookTitle);
        List<CopyResponseDTO> copiesResponseList = copiesList.stream()
                .map(copyMapper::mapToResponseDto)
                .toList();

        return new ResponseEntity<>(copiesResponseList,HttpStatus.FOUND);
    }


    @GetMapping("/search/available")
    public ResponseEntity<List<CopyResponseDTO>> getAvailableCopiesOfBookByIsbn(
            @RequestParam("book-title") String bookTitle
    ){
        List<Copy> copiesList = copyService.findAvailableCopiesByBookTitle(bookTitle);
        List<CopyResponseDTO> copiesResponseList = copiesList.stream()
                .map(copyMapper::mapToResponseDto)
                .toList();

        return new ResponseEntity<>(copiesResponseList,HttpStatus.FOUND);
    }

    @GetMapping("/{copyId}/borrow-history")
    public ResponseEntity<CopyBorrowersDTO> getAllBorrowersOfCopy(
            @PathVariable("copyId") Long copyId
    ){
        CopyBorrowersDTO copyBorrowerHistory = borrowerCopyService.getBorrowHistory(copyId);
        return new ResponseEntity<>(copyBorrowerHistory,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CopyResponseDTO> addCopyOfBook(
            @RequestBody CopyDTO copyDto
    ){
        Copy savedCopy = copyService.saveCopy(copyDto.getBookId());
        CopyResponseDTO savedCopyResponse = copyMapper.mapToResponseDto(savedCopy);
        return new ResponseEntity<>(savedCopyResponse,HttpStatus.CREATED);
    }
}
