package com.gpsolutions.hotels.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public final class HotelSearchRequestDto {
    private String name;
    private String brand;
    private String city;
    private String country;
    private Set<String> amenities;
}