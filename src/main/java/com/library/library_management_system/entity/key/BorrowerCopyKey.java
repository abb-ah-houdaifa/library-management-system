package com.library.library_management_system.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowerCopyKey implements Serializable {
    @Column(name = "borrower_id")
    private Long borrowerId;
    @Column(name = "copy_id")
    private Long copyId;
    private Date borrowDate;
}
