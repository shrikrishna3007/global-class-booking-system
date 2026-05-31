package com.project.classbooking.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOfferingRequest {

    private Long courseId;
    private Long teacherId;
    private String timezone;
}
