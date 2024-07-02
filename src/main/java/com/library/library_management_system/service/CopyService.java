package com.library.library_management_system.service;

import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Copy;
import com.library.library_management_system.entity.enumeration.CopyState;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.exception.NullAttributeException;
import com.library.library_management_system.repository.CopyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CopyService{

    private final CopyRepository copyRepository;
    private final BookService bookService;

    public List<Copy> findAllCopies() {
        return copyRepository.findAll();
    }

    public List<Copy> findAllCopiesByBookTitle(String bookTitle) {
        return copyRepository.findAllByBookTitle(bookTitle);
    }

    @Transactional
    public Copy saveCopy(Long bookId) {
        if(bookId == null) throw new NullAttributeException("Book id is not Provided");
        Book book = bookService.findBookById(bookId);

        Copy copy = Copy.builder()
                .book(book)
                .state(CopyState.GOOD)
                .available(true)
                .build();

        copyRepository.save(copy);
        return copy;
    }

    public Copy findById(Long copyId) {
        Optional<Copy> copy = copyRepository.findById(copyId);
        return copy.orElseThrow(() -> new NotFoundException("Copy with ID: " + copyId + " doesn't exists"));
    }

    public List<Copy> findAvailableCopiesByBookTitle(
            String bookTitle
    ){
        return copyRepository.findAvailableByBookTitle(bookTitle);
    }

    public void updateCopyAvailability(Copy copyId,boolean available) {
        copyRepository.updateCopyAvailability(copyId.getCopyId(), available);
    }

    //set the copy state to the returned state
    //set availability to true
    public void returnCopy(Copy copy) {

        copyRepository.updateCopy(
                copy.getCopyId(),
                copy.getState().toString(),
                true
        );
    }
}
