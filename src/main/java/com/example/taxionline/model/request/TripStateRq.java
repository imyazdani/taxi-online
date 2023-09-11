package com.example.taxionline.model.request;

import com.example.taxionline.model.enums.TripStateEnum;
import lombok.Data;

@Data
public class TripStateRq {
    private TripStateEnum tripState;
}
