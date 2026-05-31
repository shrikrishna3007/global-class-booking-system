package com.project.classbooking.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateSessionRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
