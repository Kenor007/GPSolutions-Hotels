package com.gpsolutions.hotels.service;

import com.gpsolutions.hotels.dto.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HotelService {
    HotelShortResponseDto createHotel(HotelCreateRequestDto hotelCreateRequestDto);

    void addAmenitiesToHotel(Long hotelId, Set<String> amenities);

    HotelResponseDto findHotelById(Long hotelId);

    List<HotelShortResponseDto> findAllHotels();

    List<HotelShortResponseDto> findAllHotelsByParams(HotelSearchRequestDto hotelSearchRequestDto);

    Map<String, Long> getHistogram(String param);
}