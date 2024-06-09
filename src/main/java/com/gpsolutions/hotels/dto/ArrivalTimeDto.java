package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrivalTimeDto {
    @NotBlank(message = "Check in should not be blank")
    @Size(min = 1, message = "Check in should contain at least 1 character")
    private String checkIn;

    private String checkOut = "";
}