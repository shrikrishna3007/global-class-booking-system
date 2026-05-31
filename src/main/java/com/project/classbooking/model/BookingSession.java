package com.project.classbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingSessionId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private SessionEntity session;
}
