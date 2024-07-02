package com.library.library_management_system.service;

import com.library.library_management_system.entity.Author;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService{

    private final AuthorRepository authorRepository;
    private final Validator validator;

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        validateAuthorAttributes(author);

        //save the author to the database
        return authorRepository.save(author);
    }

    public void validateAuthorAttributes(Author author){
        Set<ConstraintViolation<Author>> violations = validator.validate(author);
        if(!violations.isEmpty()){
            StringBuilder violationMessage = new StringBuilder();
            for(ConstraintViolation<Author> violation: violations){
                violationMessage.append(violation).append(", ");
            }

            throw new ConstraintViolationException(violationMessage + " ", violations);
        }
    }

    public Author findAuthorById(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if(author.isEmpty())
            throw new NotFoundException("Author with ID : " + authorId + " Not Found");

        return author.get();
    }

    //fix it
    public List<Author> findAuthorByFirstnameLastname(String firstname, String lastname) {
        if(lastname == null)
            return authorRepository.findByFirstname(firstname);

        return authorRepository.findByFirstnameAndLastname(firstname,lastname);
    }

    public Author updateAuthorDetails(Long authorId, Author newAuthorDetails) {
        Author author = findAuthorById(authorId);

        String authorFirstname = (newAuthorDetails.getFirstname() == null)? author.getFirstname() : newAuthorDetails.getFirstname();
        String authorLastname = (newAuthorDetails.getLastname() == null)? author.getLastname() : newAuthorDetails.getLastname();

        author.setFirstname(authorFirstname);
        author.setLastname(authorLastname);

        return authorRepository.save(author);
    }
}
