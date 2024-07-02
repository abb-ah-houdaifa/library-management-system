package com.library.library_management_system.service;

import com.library.library_management_system.models.book.BookSummaryDTO;
import com.library.library_management_system.entity.Author;
import com.library.library_management_system.entity.Book;
import com.library.library_management_system.entity.Editor;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.repository.BookRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService{

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final EditorService editorService;
    private final Validator validator;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book, List<Long> authorsId,Long editorId) {
        //checks whether the book attributes are provided
        validateBookAttribute(book,authorsId,editorId);

        //get the authors List
        List<Author> authorsList = fetchAuthorsByIds(authorsId);

        //assert that the editor exists
        Editor editor = editorService.findEditorById(editorId);

        //add the authors list and the editor the book entity
        book.setEditor(editor);
        book.setAuthors(authorsList);

        //save the book in the database
        bookRepository.save(book);

        return book;
    }

    public void validateBookAttribute(Book book, List<Long> authorsId, Long editorId){
        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        if(!violations.isEmpty()){
            StringBuilder violationMessage = new StringBuilder();
            for(ConstraintViolation<Book> violation: violations){
                violationMessage.append(violation.getMessage()).append(", ");
            }

            throw new ConstraintViolationException("Error occurred: " + violationMessage, violations);
        }
    }

    public List<Author> fetchAuthorsByIds(List<Long> authorsId){
        List<Author> authorsList = new ArrayList<>();

        for(Long authorId : authorsId){
            Author author = authorService.findAuthorById(authorId);
            if(author == null){
                throw new RuntimeException();
            }

            authorsList.add(author);
        }
        return authorsList;
    }

    public Book findBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.orElseThrow(() -> new NotFoundException("Book with ID : " + bookId + " Not Found"));
    }

    public Book updateBook(Long bookId, Book newBookDetails, List<Long> authorsId, Long editorId) {
        Book bookDB = this.findBookById(bookId);

        String bookTitle = (newBookDetails.getTitle() == null) ? bookDB.getTitle() : newBookDetails.getTitle();
        String bookSummary = (newBookDetails.getSummary() == null) ? bookDB.getSummary() : newBookDetails.getSummary();
        String bookIsbn = (newBookDetails.getIsbn() == null)? bookDB.getIsbn() : newBookDetails.getIsbn();

        List<Author> authorsList = (authorsId == null)? bookDB.getAuthors() : this.fetchAuthorsByIds(authorsId);
        Editor editor = (editorId == null)? bookDB.getEditor() : editorService.findEditorById(editorId);

        Book newBook = Book.builder()
                .bookId(bookId)
                .title(bookTitle)
                .isbn(bookIsbn)
                .summary(bookSummary)
                .authors(authorsList)
                .editor(editor)
                .build();

        this.bookRepository.save(newBook);

        return newBook;
    }

    public List<BookSummaryDTO> findAllBooksWrittenBy(Long authorId) {
        authorService.findAuthorById(authorId);//throws an exception if the author is not found

        return bookRepository.fetchBooksWrittenBy(authorId);
    }
}
