package com.project.classbooking.service;

import com.project.classbooking.dto.request.BookOfferingRequest;
import com.project.classbooking.dto.response.BookingResponse;
import com.project.classbooking.dto.response.SessionResponse;
import com.project.classbooking.model.BookingEntity;
import com.project.classbooking.model.BookingSession;
import com.project.classbooking.model.OfferingEntity;
import com.project.classbooking.model.SessionEntity;
import com.project.classbooking.repository.BookingRepository;
import com.project.classbooking.repository.OfferingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final OfferingRepository offeringRepository;
    private final BookingRepository bookingRepository;


    @Transactional
    public BookingResponse createBookingForOffering(BookOfferingRequest request){
        // fetch offering

        OfferingEntity offering = offeringRepository.findById(request.getOfferingId())
                .orElseThrow(()-> new EntityNotFoundException("Details not found."));

        // Get all sessions of the retrieved offering
        List<SessionEntity> sessionList = offering.getSessions();

        validateSessions(sessionList);

        // Fetch existing bookings of parent
        List<BookingEntity> existingBookings =
                bookingRepository.findByParentId(request.getParentId());

        // 4. Extract all previously booked sessions
        List<SessionEntity> existingSessions = new ArrayList<>();

        for (BookingEntity booking : existingBookings) {
            if (booking.getBookingSessions() != null) {
                for (BookingSession bs : booking.getBookingSessions()) {
                    existingSessions.add(bs.getSession());
                }
            }
        }

        // 5. Conflict detection
        for (SessionEntity newSession : sessionList) {

            Instant newStart = newSession.getStartTime();
            Instant newEnd = newSession.getEndTime();

            for (SessionEntity existing : existingSessions) {

                Instant existingStart = existing.getStartTime();
                Instant existingEnd = existing.getEndTime();

                boolean overlap =
                        existingStart.isBefore(newEnd) &&
                                existingEnd.isAfter(newStart);

                if (overlap) {
                    throw new IllegalStateException("Booking conflict detected");
                }
            }
        }

        // 6. Create booking
        BookingEntity booking = new BookingEntity();
        booking.setParentId(request.getParentId());
        booking.setOffering(offering);
        booking.setBookedAt(Instant.now());

        // 7. Create booking sessions
        List<BookingSession> bookingSessions = new ArrayList<>();

        for (SessionEntity session : sessionList) {
            BookingSession bs = new BookingSession();
            bs.setBooking(booking);
            bs.setSession(session);
            bookingSessions.add(bs);
        }

        booking.setBookingSessions(bookingSessions);

        // 8. Save booking (cascade saves bookingSessions)
        BookingEntity saved = bookingRepository.save(booking);

        // 9. Map to response
        BookingResponse response = new BookingResponse();
        response.setBookingId(saved.getBookingId());
        response.setOfferingId(offering.getOfferingId());

        List<SessionResponse> sessionResponses = new ArrayList<>();

        for (SessionEntity session : sessionList) {
            SessionResponse sr = new SessionResponse();

            sr.setSessionId(session.getSessionId());

            LocalDateTime start = session.getStartTime()
                    .atZone(ZoneId.of(offering.getTimezone()))
                    .toLocalDateTime();

            LocalDateTime end = session.getEndTime()
                    .atZone(ZoneId.of(offering.getTimezone()))
                    .toLocalDateTime();

            sr.setStartTime(start);
            sr.setEndTime(end);

            sessionResponses.add(sr);
        }

        response.setSessions(sessionResponses);

        return response;
    }

    private void validateSessions(List<SessionEntity> sessionList) {
        if (sessionList == null || sessionList.isEmpty()){
            throw new IllegalStateException("No sessions available for this offering.");
        }
    }

    public List<BookingResponse> getBookingsByParent(Long parentId) {

        List<BookingEntity> bookings = bookingRepository.findByParentId(parentId);

        List<BookingResponse> responses = new ArrayList<>();

        for (BookingEntity booking : bookings) {

            BookingResponse response = new BookingResponse();
            response.setBookingId(booking.getBookingId());
            response.setOfferingId(booking.getOffering().getOfferingId());

            List<SessionResponse> sessionResponses = new ArrayList<>();

            for (BookingSession bs : booking.getBookingSessions()) {

                SessionEntity session = bs.getSession();

                SessionResponse sr = new SessionResponse();
                sr.setSessionId(session.getSessionId());

                LocalDateTime start = session.getStartTime()
                        .atZone(ZoneId.of(booking.getOffering().getTimezone()))
                        .toLocalDateTime();

                LocalDateTime end = session.getEndTime()
                        .atZone(ZoneId.of(booking.getOffering().getTimezone()))
                        .toLocalDateTime();

                sr.setStartTime(start);
                sr.setEndTime(end);

                sessionResponses.add(sr);
            }

            response.setSessions(sessionResponses);

            responses.add(response);
        }

        return responses;
    }
}
