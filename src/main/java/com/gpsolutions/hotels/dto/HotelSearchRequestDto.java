package com.gpsolutions.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public final class HotelSearchRequestDto {
    @Schema(example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(example = "Hilton", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String brand;

    @Schema(example = "Minsk", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String city;

    @Schema(example = "Belarus", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String country;

    @Schema(example = "[\"Free parking\", \"Free WiFi\", \"Non-smoking rooms\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Set<String> amenities;
}