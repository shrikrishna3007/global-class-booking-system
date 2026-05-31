package com.project.classbooking.service;

import com.project.classbooking.dto.request.CreateOfferingRequest;
import com.project.classbooking.dto.request.CreateSessionRequest;
import com.project.classbooking.dto.response.OfferingResponse;
import com.project.classbooking.dto.response.SessionResponse;
import com.project.classbooking.model.CourseEntity;
import com.project.classbooking.model.OfferingEntity;
import com.project.classbooking.model.SessionEntity;
import com.project.classbooking.repository.CourseRepository;
import com.project.classbooking.repository.OfferingRepository;
import com.project.classbooking.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OfferingService {


    private final CourseRepository courseRepository;
    private final OfferingRepository offeringRepository;
    private final SessionRepository sessionRepository;

    public OfferingResponse createOffering(CreateOfferingRequest createOfferingRequest){
        // Fetch course details
        CourseEntity course = courseRepository.findById(createOfferingRequest.getCourseId())
                .orElseThrow(()-> new RuntimeException("Course not found !!"));

        // Create Offering
        OfferingEntity offering = new OfferingEntity();
        offering.setCourseEntity(course);
        offering.setTeacherId(createOfferingRequest.getTeacherId());
        offering.setTimezone(createOfferingRequest.getTimezone());
        offering.setCreatedAt(Instant.now());

        // Save offering
        OfferingEntity savedData = offeringRepository.save(offering);

        // Map to response
        OfferingResponse response = new OfferingResponse();
        response.setOfferingId(savedData.getOfferingId());
        response.setCourseId(course.getCourseId());
        response.setTeacherId(savedData.getTeacherId());
        response.setTimezone(savedData.getTimezone());

        return response;
    }


    public void addSessions(Long offeringId, List<CreateSessionRequest> sessionRequests) {
        // get offering
        OfferingEntity offering = offeringRepository.findById(offeringId)
                .orElseThrow(()-> new RuntimeException("Offering not found !"));

        ZoneId zone = ZoneId.of(offering.getTimezone());

        List<SessionEntity> sessions = new ArrayList<>();

        for (CreateSessionRequest request: sessionRequests){
            // Validation
            if (!request.getStartTime().isBefore(request.getEndTime())){
                throw new IllegalArgumentException("Start time must be before end time.");
            }

            Instant start = request.getStartTime()
                    .atZone(zone)
                    .toInstant();

            Instant end = request.getEndTime()
                    .atZone(zone)
                    .toInstant();

            SessionEntity session = new SessionEntity();
            session.setOffering(offering);
            session.setStartTime(start);
            session.setEndTime(end);

            sessions.add(session);
        }

        sessionRepository.saveAll(sessions);
    }

    public List<OfferingResponse> getOfferingsByTeacher(Long teacherId) {

        List<OfferingEntity> offerings = offeringRepository.findByTeacherId(teacherId);

        List<OfferingResponse> responseList = new ArrayList<>();

        for (OfferingEntity offering : offerings){
            OfferingResponse response = new OfferingResponse();
            response.setOfferingId(offering.getOfferingId());
            response.setCourseId(offering.getCourseEntity().getCourseId());
            response.setTeacherId(offering.getTeacherId());
            response.setTimezone(offering.getTimezone());

            List<SessionResponse> sessionResponses = new ArrayList<>();

            if (offering.getSessions() != null){
                for (SessionEntity session : offering.getSessions()){
                    SessionResponse sessionResponse = new SessionResponse();

                    sessionResponse.setSessionId(session.getSessionId());

                    // Convert Instant to Local date and time
                    LocalDateTime start = session.getStartTime()
                            .atZone(ZoneId.of(offering.getTimezone()))
                            .toLocalDateTime();

                    LocalDateTime end = session.getEndTime()
                            .atZone(ZoneId.of(offering.getTimezone()))
                            .toLocalDateTime();

                    sessionResponse.setStartTime(start);
                    sessionResponse.setEndTime(end);

                    sessionResponses.add(sessionResponse);
                }
            }
            response.setSessions(sessionResponses);

            responseList.add(response);
        }
        return responseList;
    }
}
