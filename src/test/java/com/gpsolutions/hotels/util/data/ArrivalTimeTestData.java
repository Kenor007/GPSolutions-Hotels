package com.gpsolutions.hotels.util.data;

import com.gpsolutions.hotels.dto.ArrivalTimeDto;
import com.gpsolutions.hotels.model.ArrivalTime;

public final class ArrivalTimeTestData {
    public static final String CHECK_IN = "14:00";
    public static final String CHECK_OUT = "12:00";

    private ArrivalTimeTestData() {
    }

    public static ArrivalTime getArrivalTime() {
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(CHECK_IN);
        arrivalTime.setCheckOut(CHECK_OUT);
        return arrivalTime;
    }

    public static ArrivalTimeDto getArrivalTimeDto() {
        ArrivalTimeDto arrivalTimeDto = new ArrivalTimeDto();
        arrivalTimeDto.setCheckIn(CHECK_IN);
        arrivalTimeDto.setCheckOut(CHECK_OUT);
        return arrivalTimeDto;
    }
}