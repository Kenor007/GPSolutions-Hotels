package com.gpsolutions.hotels.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelCreateRequestDto {
    @NotBlank(message = "Hotel name should not be blank")
    @Size(min = 1, message = "Hotel name should contain at least 1 character")
    private String name;

    @NotNull(message = "Description should not be null")
    private String description = "";

    @NotBlank(message = "Hotel brand should not be blank")
    @Size(min = 1, message = "Hotel brand should contain at least 1 character")
    private String brand;

    @Valid
    private AddressDto address;

    @Valid
    private ContactDto contacts;

    @Valid
    private ArrivalTimeDto arrivalTime;
}