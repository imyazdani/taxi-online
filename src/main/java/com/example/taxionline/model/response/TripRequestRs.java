package com.example.taxionline.model.response;

import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripRequestRs {
    private TripStateEnum tripState;
    private LocalDateTime tripTime;
}
