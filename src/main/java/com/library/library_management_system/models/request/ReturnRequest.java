package com.library.library_management_system.models.request;

import com.library.library_management_system.entity.enumeration.CopyState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnRequest {
    private Long borrowerId;
    private Long copyId;
    private CopyState state;
}
