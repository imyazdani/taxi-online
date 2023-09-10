package com.example.taxionline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "driver")
@Getter
@Setter
public class DriverEntity extends UserEntity{
    private String car;

    @OneToMany(mappedBy = "driverEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TripEntity> tripList = new HashSet<>();
}
