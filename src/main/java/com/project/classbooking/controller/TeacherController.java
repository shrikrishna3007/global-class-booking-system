package com.project.classbooking.controller;

import com.project.classbooking.dto.request.CreateOfferingRequest;
import com.project.classbooking.dto.request.CreateSessionRequest;
import com.project.classbooking.dto.response.OfferingResponse;
import com.project.classbooking.service.OfferingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@AllArgsConstructor
public class TeacherController {

    private final  OfferingService offeringService;

    @PostMapping("/offerings")
    public ResponseEntity<?> createOffering(@RequestBody CreateOfferingRequest request){
        try {
            OfferingResponse response =  offeringService.createOffering(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/offerings/{offeringId}/sessions")
    public ResponseEntity<?> addSessions(@PathVariable Long offeringId, @RequestBody List<CreateSessionRequest> sessionRequests){
        try {
            offeringService.addSessions(offeringId, sessionRequests);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sessions added successfully !!!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/offerings")
    public ResponseEntity<?> getOfferings(@RequestParam Long teacherId){
        try {
            List<OfferingResponse> offerings = offeringService.getOfferingsByTeacher(teacherId);
            return ResponseEntity.status(HttpStatus.OK).body(offerings);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
