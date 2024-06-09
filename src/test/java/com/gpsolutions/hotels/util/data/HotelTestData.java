package com.gpsolutions.hotels.util.data;

import com.gpsolutions.hotels.dto.HotelCreateRequestDto;
import com.gpsolutions.hotels.dto.HotelResponseDto;
import com.gpsolutions.hotels.dto.HotelSearchRequestDto;
import com.gpsolutions.hotels.dto.HotelShortResponseDto;
import com.gpsolutions.hotels.model.Hotel;

import java.util.Set;

import static com.gpsolutions.hotels.util.data.AddressTestData.*;
import static com.gpsolutions.hotels.util.data.ArrivalTimeTestData.getArrivalTime;
import static com.gpsolutions.hotels.util.data.ArrivalTimeTestData.getArrivalTimeDto;
import static com.gpsolutions.hotels.util.data.ContactTestData.*;

public final class HotelTestData {
    public static final Long ID = 1L;
    public static final String NAME = "DoubleTree by Hilton Minsk";
    public static final String DESCRIPTION = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms";
    public static final String BRAND = "Hilton";
    public static final Set<String> AMENITIES = Set.of("Free parking", "Free WiFi", "Non-smoking rooms");

    private HotelTestData() {
    }

    public static Hotel getHotel() {
        Hotel hotel = new Hotel();
        hotel.setName(NAME);
        hotel.setDescription(DESCRIPTION);
        hotel.setBrand(BRAND);
        hotel.setAddress(getAddress());
        hotel.setContacts(getContact());
        hotel.setArrivalTime(getArrivalTime());
        return hotel;
    }

    public static HotelCreateRequestDto getHotelCreateRequestDto() {
        HotelCreateRequestDto hotelCreateRequestDto = new HotelCreateRequestDto();
        hotelCreateRequestDto.setName(NAME);
        hotelCreateRequestDto.setDescription(DESCRIPTION);
        hotelCreateRequestDto.setBrand(BRAND);
        hotelCreateRequestDto.setAddress(getAddressDto());
        hotelCreateRequestDto.setContacts(getContactDto());
        hotelCreateRequestDto.setArrivalTime(getArrivalTimeDto());
        return hotelCreateRequestDto;
    }

    public static HotelShortResponseDto getHotelShortResponseDto() {
        HotelShortResponseDto hotelShortResponseDto = new HotelShortResponseDto();
        hotelShortResponseDto.setId(ID);
        hotelShortResponseDto.setName(NAME);
        hotelShortResponseDto.setDescription(DESCRIPTION);
        hotelShortResponseDto.setAddress(getAddress().toString());
        hotelShortResponseDto.setPhone(PHONE);
        return hotelShortResponseDto;
    }

    public static HotelResponseDto getHotelResponseDto() {
        HotelResponseDto hotelResponseDto = new HotelResponseDto();
        hotelResponseDto.setId(ID);
        hotelResponseDto.setName(NAME);
        hotelResponseDto.setBrand(BRAND);
        hotelResponseDto.setAddress(getAddressDto());
        hotelResponseDto.setContacts(getContactDto());
        hotelResponseDto.setArrivalTime(getArrivalTimeDto());
        hotelResponseDto.setAmenities(AMENITIES);
        return hotelResponseDto;
    }

    public static HotelSearchRequestDto getHotelSearchRequestDto() {
        HotelSearchRequestDto hotelSearchRequestDto = new HotelSearchRequestDto();
        hotelSearchRequestDto.setName(NAME);
        hotelSearchRequestDto.setBrand(BRAND);
        hotelSearchRequestDto.setCity(CITY);
        hotelSearchRequestDto.setCountry(COUNTRY);
        hotelSearchRequestDto.setAmenities(AMENITIES);
        return hotelSearchRequestDto;
    }
}