# Global Class Offering Booking System

## Project Overview

This project implements a backend service for a global live-learning platform where teachers create course offerings and parents can book them.

The system supports:

* Course → Offering → Sessions hierarchy
* Timezone-aware scheduling
* Booking entire offering (not individual sessions)
* Conflict detection to prevent overlapping bookings

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* Lombok

---

## Setup Instructions

### 1. Clone Repository

```
git clone <your-repo-url>
cd class-offering-booking-system
```

### 2. Create Database

```
CREATE DATABASE class_booking_system;
```

### 3. Configure application.yml

```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/database_name
    username: your_username
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 4. Run Application

```
mvn spring-boot:run
```

---

## Environment Variables

* DB_URL
* DB_USERNAME
* DB_PASSWORD

---

## API Documentation

### Teacher APIs

#### Create Offering

POST /api/teacher/offerings

```
{
  "courseId": 1,
  "teacherId": 101,
  "timezone": "Asia/Kolkata"
}
```

---

#### Add Sessions

POST /api/teacher/offerings/{offeringId}/sessions

```
[
  {
    "startTime": "2026-06-01T18:00:00",
    "endTime": "2026-06-01T19:00:00"
  }
]
```

---

#### Get Offerings

GET /api/teacher/offerings?teacherId=101

---

### Parent APIs

#### Book Offering

POST /api/parent/bookings

```
{
  "parentId": 1,
  "offeringId": 1
}
```

---

#### Get Bookings

GET /api/parent/bookings?parentId=1

---

## Database Schema Overview

Course

* course_id (PK)
* course_name
* course_description
* created_at

Offering

* offering_id (PK)
* course_id (FK)
* teacher_id
* timezone
* created_at

Session

* session_id (PK)
* offering_id (FK)
* start_time
* end_time

Booking

* booking_id (PK)
* parent_id
* offering_id (FK)
* booked_at

Booking_Session

* booking_session_id (PK)
* booking_id (FK)
* session_id (FK)

---

## Assumptions Made

* Parent books entire offering
* Session times stored in UTC (Instant)
* Teacher timezone used for input conversion
* No authentication implemented

---

## Concurrency Handling Approach

* Used @Transactional for booking flow
* Ensures atomic operations
* Prevents partial writes

Note: Advanced locking not implemented due to scope.

---

## Timezone Handling Approach

* Input: LocalDateTime (teacher timezone)
* Converted to Instant (UTC) for storage
* Converted back during response

---

## Steps to Run Locally

1. Start MySQL
2. Create database
3. Run Spring Boot app
4. Insert course manually
5. Test APIs using Postman

---

## Sample Data Setup

```
INSERT INTO course (course_id, course_name, course_description, created_at)
VALUES (1, 'Test Course', 'Demo Course', NOW());
```

---

## Known Limitations

* Conflict detection implemented but needs refinement
* Some edge cases may not be fully handled
* No pagination or filtering
* No authentication

---

## Features Implemented

* Course → Offering → Session structure
* Booking system
* Basic conflict detection
* REST APIs for teacher and parent
* Timezone handling

---
