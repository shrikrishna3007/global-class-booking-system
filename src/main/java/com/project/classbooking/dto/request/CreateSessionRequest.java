package com.project.classbooking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateSessionRequest {

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
}
