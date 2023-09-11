package com.example.taxionline.repository;

import com.example.taxionline.model.entity.TripEntity;
import com.example.taxionline.model.enums.TripStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {
    List<TripEntity> findByTripState(TripStateEnum tripState);
}
