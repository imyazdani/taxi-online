package com.example.taxionline.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "gpsLocation")
public class GpsLocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long x;
    private Long y;
}
