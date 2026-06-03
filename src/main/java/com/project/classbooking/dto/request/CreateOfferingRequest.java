package com.project.classbooking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOfferingRequest {

    @NotNull(message = "Course ID is required")
    private Long courseId;
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;
    @NotNull(message = "Timezone is required")
    private String timezone;
}
