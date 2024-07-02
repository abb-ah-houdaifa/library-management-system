package com.library.library_management_system.entity;

import com.library.library_management_system.entity.key.BorrowerCopyKey;
import com.library.library_management_system.entity.enumeration.CopyState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerCopy {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name= "borrowerId", column = @Column(name="borrower_id", insertable = false, updatable = false)),
            @AttributeOverride(name= "copyId", column = @Column(name="copy_id", insertable = false, updatable = false))
    })
    private BorrowerCopyKey borrowingKey;

    @ManyToOne(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @MapsId("copyId")
    private Copy copy;

    @ManyToOne(
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @MapsId("borrowerId")
    private Borrower borrower;

    private Date supposedReturnDate;

    private Date actualReturnDate;

    @Enumerated(EnumType.STRING)
    private CopyState copyState;
}
