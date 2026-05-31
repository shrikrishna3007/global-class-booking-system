package com.project.classbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class OfferingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offeringId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity courseEntity;

    private Long teacherId;
    private String timezone;
    private Instant createdAt;

    @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL)
    private List<SessionEntity> sessions;
}
