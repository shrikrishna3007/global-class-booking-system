package com.project.classbooking.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OfferingResponse {

    private Long offeringId;
    private Long courseId;
    private Long teacherId;
    private String timezone;

    private List<SessionResponse> sessions;
}
