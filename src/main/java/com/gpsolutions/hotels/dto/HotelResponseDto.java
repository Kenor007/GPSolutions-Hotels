package com.gpsolutions.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class HotelResponseDto {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(example = "Hilton", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brand;

    @Schema(implementation = AddressDto.class, requiredMode = Schema.RequiredMode.REQUIRED)
    private AddressDto address;

    @Schema(implementation = ContactDto.class, requiredMode = Schema.RequiredMode.REQUIRED)
    private ContactDto contacts;

    @Schema(implementation = ArrivalTimeDto.class, requiredMode = Schema.RequiredMode.REQUIRED)
    private ArrivalTimeDto arrivalTime;

    @Schema(example = "[\"Free parking\", \"Free WiFi\", \"Non-smoking rooms\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> amenities;
}