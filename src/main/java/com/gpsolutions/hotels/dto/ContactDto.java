package com.gpsolutions.hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {
    @NotBlank(message = "Phone number should not be blank")
    @Size(min = 1, message = "Phone number should contain at least 1 character")
    private String phone;

    @NotBlank(message = "Email should not be blank")
    @Size(min = 1, message = "Email should contain at least 1 character")
    private String email;
}