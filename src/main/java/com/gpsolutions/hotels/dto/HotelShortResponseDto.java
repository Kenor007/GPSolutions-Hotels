package com.gpsolutions.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelShortResponseDto {
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "9 Pobediteley Avenue, Minsk, 220004, Belarus", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @Schema(example = "+375 17 309-80-00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;
}