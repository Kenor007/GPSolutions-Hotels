package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    @NotNull(message = "House number should not be empty")
    @Positive(message = "House number must not be less than one")
    private Integer houseNumber;

    @NotBlank(message = "Street name should not be blank")
    @Size(min = 1, message = "Street name should contain at least 1 character")
    private String street;

    @NotBlank(message = "City name should not be blank")
    @Size(min = 1, message = "City name should contain at least 1 character")
    private String city;

    @NotBlank(message = "Country name should not be blank")
    @Size(min = 1, message = "Country name should contain at least 1 character")
    private String country;

    @NotBlank(message = "Post code should not be blank")
    @Size(min = 1, message = "Post code should contain at least 1 character")
    private String postCode;
}