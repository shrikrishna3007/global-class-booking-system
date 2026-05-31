package com.project.classbooking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingResponse {

    private Long bookingId;
    private Long offeringId;

    private List<SessionResponse> sessions;
}
