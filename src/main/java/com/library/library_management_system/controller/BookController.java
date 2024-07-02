package com.library.library_management_system.controller;

import com.library.library_management_system.models.book.BookRequestDTO;
import com.library.library_management_system.models.book.BookSummaryDTO;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.mapper.BookMapper;
import com.library.library_management_system.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> listAllBooks(){
        List<Book> booksList = bookService.findAllBooks();

        return new ResponseEntity<>(booksList, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findBookById(
            @PathVariable("bookId") Long bookId
    ){
        Book book = bookService.findBookById(bookId);
        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @GetMapping("written-by/{authorId}")
    public ResponseEntity<List<BookSummaryDTO>> findAllBooksWrittenByAuthor(
            @PathVariable("authorId") Long authorId
    ){
        List<BookSummaryDTO> booksList = bookService.findAllBooksWrittenBy(authorId);

        return new ResponseEntity<>(booksList,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @RequestBody @Valid BookRequestDTO bookDto
    ){
        Book bookEntity = bookMapper.mapToEntity(bookDto);
        Book savedBook = bookService.saveBook(
                bookEntity,
                bookDto.getAuthorsId(),//authors ids list
                bookDto.getEditorId()//editor id
        );

        return new ResponseEntity<>(savedBook,HttpStatus.CREATED);
    }

    @PutMapping("/edit/{bookId}")
    public ResponseEntity<Book> editBook(
            @PathVariable("bookId") Long bookId,
            @RequestBody @Valid BookRequestDTO newBookDtoDetails
    ){
        Book newBookDetails = bookMapper.mapToEntity(newBookDtoDetails);

        Book updatedBook = bookService.updateBook(
                bookId,
                newBookDetails,
                newBookDtoDetails.getAuthorsId(),
                newBookDtoDetails.getEditorId()
        );
        return new ResponseEntity<>(updatedBook,HttpStatus.ACCEPTED);
    }
}
