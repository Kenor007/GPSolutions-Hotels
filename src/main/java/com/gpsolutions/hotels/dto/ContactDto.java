package com.gpsolutions.hotels.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {
    @Schema(example = "+375 17 309-80-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Phone number should not be blank")
    @Size(min = 1, message = "Phone number should contain at least 1 character")
    private String phone;

    @Schema(example = "doubletreeminsk.info@hilton.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email should not be blank")
    @Size(min = 1, message = "Email should contain at least 1 character")
    @Email(message = "Email must have the format of an email address")
    private String email;
}