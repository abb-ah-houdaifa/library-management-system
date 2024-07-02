package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    List<Author> findByFirstname(String firstname);

    List<Author> findByFirstnameAndLastname(String firstname, String lastname);
}
