package com.project.classbooking.controller;

import com.project.classbooking.dto.request.BookOfferingRequest;
import com.project.classbooking.dto.response.BookingResponse;
import com.project.classbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
@AllArgsConstructor
public class ParentController {

    private final BookingService bookingService;

    @Operation(summary = "Create booking")
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> bookOffering(@Valid @RequestBody BookOfferingRequest request){
        BookingResponse response = bookingService.createBookingForOffering(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get bookings by parentId")
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getBookings(@RequestParam Long parentId){
        List<BookingResponse> responses = bookingService.getBookingsByParent(parentId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
