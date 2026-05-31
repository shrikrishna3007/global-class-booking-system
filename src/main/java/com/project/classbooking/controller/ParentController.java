package com.project.classbooking.controller;

import com.project.classbooking.dto.request.BookOfferingRequest;
import com.project.classbooking.dto.response.BookingResponse;
import com.project.classbooking.service.BookingService;
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

    @PostMapping("/bookings")
    public ResponseEntity<?> bookOffering(@RequestBody BookOfferingRequest request){
        try {
            BookingResponse response = bookingService.bookOffering(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> getBookings(@RequestParam Long parentId){
        try {
            List<BookingResponse> responses = bookingService.getBookingsByParent(parentId);
            return ResponseEntity.status(HttpStatus.OK).body(responses);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
