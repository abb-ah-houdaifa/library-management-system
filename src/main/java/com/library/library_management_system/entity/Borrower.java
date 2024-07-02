package com.library.library_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrower_seq_gen")
    @SequenceGenerator(name = "borrower_seq_gen", sequenceName = "borrower_seq", allocationSize = 1)
    private Long borrowerId;

    @NotBlank(message = "Borrower firstname must not be blank")
    private String firstname;

    @NotBlank(message = "Borrower lastname must not be blank")
    private String lastname;

    @NotNull(message = "Phone number must not be null")
    @Pattern(
            regexp = "^0[5-7][0-9]{8}",
            message = "Phone number must contain 10 digits"
    )
    @Column(unique = true)
    private String phoneNumber;
    private boolean blocked;
    private Date blockDate;
}
