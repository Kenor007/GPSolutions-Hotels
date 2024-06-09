package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelCreateRequestDto {
    @NotBlank(message = "Hotel name should not be blank")
    @Size(min = 1, message = "Hotel name should contain at least 1 character")
    private String name;

    private String description = "";

    @NotBlank(message = "Hotel brand should not be blank")
    @Size(min = 1, message = "Hotel brand should contain at least 1 character")
    private String brand;

    private AddressDto address;

    private ContactDto contacts;

    private ArrivalTimeDto arrivalTime;
}