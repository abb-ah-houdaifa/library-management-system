package com.library.library_management_system.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowRequest {
    private Long borrowerId;
    private Long copyId;
    private Integer borrowPeriod;
}
