package com.project.classbooking.controller;

import com.project.classbooking.dto.request.CreateOfferingRequest;
import com.project.classbooking.dto.request.CreateSessionRequest;
import com.project.classbooking.dto.response.OfferingResponse;
import com.project.classbooking.service.OfferingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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

    @Operation(summary = "Create course offering")
    @PostMapping("/offerings")
    public ResponseEntity<OfferingResponse> createOffering(@Valid @RequestBody CreateOfferingRequest request){
        OfferingResponse response =  offeringService.createOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Add sessions to offering")
    @PostMapping("/offerings/{offeringId}/sessions")
    public ResponseEntity<String> addSessions(@PathVariable Long offeringId, @Valid @RequestBody List<CreateSessionRequest> sessionRequests){
        offeringService.addSessions(offeringId, sessionRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sessions added successfully !!!");
    }

    @Operation(summary = "Get offerings by teacherId")
    @GetMapping("/offerings")
    public ResponseEntity<List<OfferingResponse>> getOfferings(@RequestParam Long teacherId){
        List<OfferingResponse> offerings = offeringService.getOfferingsByTeacher(teacherId);
        return ResponseEntity.status(HttpStatus.OK).body(offerings);
    }
}
