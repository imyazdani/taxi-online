package com.example.taxionline.model.dto;

import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

@Data
public class TripStateDto {
    private Long id;
    private String username;
    private TripStateEnum tripState;
}
