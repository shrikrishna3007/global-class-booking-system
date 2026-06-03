package com.project.classbooking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookOfferingRequest {

    @NotNull(message = "Parent ID is required")
    private Long parentId;
    @NotNull(message = "Offering ID is required")
    private Long offeringId;
}
