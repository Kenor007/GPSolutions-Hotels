package com.gpsolutions.hotels.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class HotelResponseDto {
    private Long id;
    private String name;
    private String brand;
    private AddressDto address;
    private ContactDto contacts;
    private ArrivalTimeDto arrivalTime;
    private Set<String> amenities;
}