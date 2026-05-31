package com.project.classbooking.repository;

import com.project.classbooking.model.OfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferingRepository extends JpaRepository<OfferingEntity, Long> {
    List<OfferingEntity> findByTeacherId(Long teacherId);
}
