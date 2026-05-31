package com.project.classbooking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SessionResponse {

    private Long sessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
