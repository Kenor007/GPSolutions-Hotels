package com.gpsolutions.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelCreateRequestDto {
    @Schema(example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Hotel name should not be blank")
    @Size(min = 1, message = "Hotel name should contain at least 1 character")
    private String name;

    @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = "Description should not be null")
    private String description = "";

    @Schema(example = "Hilton", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Hotel brand should not be blank")
    @Size(min = 1, message = "Hotel brand should contain at least 1 character")
    private String brand;

    @Schema(implementation = AddressDto.class, requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private AddressDto address;

    @Schema(implementation = ContactDto.class, requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private ContactDto contacts;

    @Schema(implementation = ArrivalTimeDto.class, requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private ArrivalTimeDto arrivalTime;
}