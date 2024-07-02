package com.library.library_management_system.entity;

import com.library.library_management_system.entity.enumeration.CopyState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copy_seq_gen")
    @SequenceGenerator(name = "copy_seq_gen", sequenceName = "copy_seq", allocationSize = 1)
    private Long copyId;
    private boolean available = true;
    @Enumerated(EnumType.STRING)
    private CopyState state;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "book_id",
            referencedColumnName = "bookId"
    )
    private Book book;
}
