package com.library.library_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_seq_gen")
    @SequenceGenerator(name = "book_seq_gen", sequenceName = "book_seq", allocationSize = 1)
    private Long bookId;

    @NotBlank(message = "Book ISBN must not be blank")
    @Column(unique = true)
    private String isbn;

    @NotBlank(message = "Book title must not be blank")
    private String title;

    @Size(
            max = 1000,
            message = "Book summary must not exceed 1000 characters"
    )
    private String summary;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "bookId"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id",
                    referencedColumnName = "authorId"
            )
    )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(
            name = "editor_id",
            referencedColumnName = "editorId"
    )
    private Editor editor;
}
