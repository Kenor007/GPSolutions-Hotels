package com.gpsolutions.hotels.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelShortResponseDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}