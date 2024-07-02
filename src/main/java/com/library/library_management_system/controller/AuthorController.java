package com.library.library_management_system.controller;

import com.library.library_management_system.entity.Author;
import com.library.library_management_system.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("")
    public ResponseEntity<List<Author>> findAllAuthors(){
        List<Author> authorsList = authorService.findAllAuthors();

        return new ResponseEntity<>(authorsList, HttpStatus.FOUND);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> findAuthor(
            @PathVariable("authorId") Long authorId
    ){
        Author author = authorService.findAuthorById(authorId);

        return new ResponseEntity<>(author, HttpStatus.FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Author>> findAuthorByFirstnameLastname(
            @RequestParam("first-name") String firstname,
            @RequestParam(value = "last-name", required = false)String lastname
    ){
        List<Author> matchingAuthors = authorService.findAuthorByFirstnameLastname(
                firstname,
                lastname
        );

            return new ResponseEntity<>(matchingAuthors, HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(
            @RequestBody @Valid Author author
    ){
        Author savedAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(savedAuthor,HttpStatus.CREATED);
    }

    @PutMapping("/edit/{authorId}")
    public ResponseEntity<Author> editAuthor(
            @PathVariable("authorId")Long authorId,
            @RequestBody Author newAuthorDetails
    ){
        Author updatedAuthor = authorService.updateAuthorDetails(
                authorId,
                newAuthorDetails
        );

        return new ResponseEntity<>(updatedAuthor,HttpStatus.ACCEPTED);
    }
}
