package com.gpsolutions.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArrivalTimeDto {
    @Schema(example = "14:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Check in should not be blank")
    @Size(min = 1, message = "Check in should contain at least 1 character")
    private String checkIn;

    @Schema(example = "12:00", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @NotNull(message = "checkOut should not be null")
    private String checkOut = "";
}