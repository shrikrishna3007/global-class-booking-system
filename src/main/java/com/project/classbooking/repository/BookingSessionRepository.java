package com.project.classbooking.repository;

import com.project.classbooking.model.BookingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSessionRepository extends JpaRepository<BookingSession, Long> {
}
