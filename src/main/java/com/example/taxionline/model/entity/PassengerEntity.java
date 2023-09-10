package com.example.taxionline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "passenger")
@Getter
@Setter
public class PassengerEntity extends UserEntity{

    @OneToMany(mappedBy = "passengerEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TripEntity> tripList = new HashSet<>();
}
