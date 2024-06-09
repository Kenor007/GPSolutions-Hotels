package com.gpsolutions.hotels.mapper;

import com.gpsolutions.hotels.dto.HotelCreateRequestDto;
import com.gpsolutions.hotels.dto.HotelResponseDto;
import com.gpsolutions.hotels.dto.HotelShortResponseDto;
import com.gpsolutions.hotels.model.Hotel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.gpsolutions.hotels.util.data.HotelTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelMapperTest {
    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    @Test
    @DisplayName("hotelCreateRequestDtoToHotel method")
    void shouldMapHotelCreateRequestDtoToHotelCorrectly() {
        HotelCreateRequestDto hotelCreateRequestDto = getHotelCreateRequestDto();

        Hotel hotel = hotelMapper.hotelCreateRequestDtoToHotel(hotelCreateRequestDto);

        assertEquals(hotelCreateRequestDto.getName(), hotel.getName());
        assertEquals(hotelCreateRequestDto.getDescription(), hotel.getDescription());
        assertEquals(hotelCreateRequestDto.getBrand(), hotel.getBrand());
        assertEquals(hotelCreateRequestDto.getAddress().getHouseNumber(), hotel.getAddress().getHouseNumber());
        assertEquals(hotelCreateRequestDto.getAddress().getStreet(), hotel.getAddress().getStreet());
        assertEquals(hotelCreateRequestDto.getAddress().getCity(), hotel.getAddress().getCity());
        assertEquals(hotelCreateRequestDto.getAddress().getCountry(), hotel.getAddress().getCountry());
        assertEquals(hotelCreateRequestDto.getAddress().getPostCode(), hotel.getAddress().getPostCode());
        assertEquals(hotelCreateRequestDto.getContacts().getPhone(), hotel.getContacts().getPhone());
        assertEquals(hotelCreateRequestDto.getContacts().getEmail(), hotel.getContacts().getEmail());
        assertEquals(hotelCreateRequestDto.getArrivalTime().getCheckIn(), hotel.getArrivalTime().getCheckIn());
        assertEquals(hotelCreateRequestDto.getArrivalTime().getCheckOut(), hotel.getArrivalTime().getCheckOut());
    }

    @Test
    @DisplayName("hotelToHotelShortResponseDto method")
    void shouldMapHotelToHotelShortResponseDtoCorrectly() {
        Hotel hotel = getHotel();
        hotel.setId(ID);

        HotelShortResponseDto hotelShortResponseDto = hotelMapper.hotelToHotelShortResponseDto(hotel);

        assertEquals(hotel.getId(), hotelShortResponseDto.getId());
        assertEquals(hotel.getName(), hotelShortResponseDto.getName());
        assertEquals(hotel.getDescription(), hotelShortResponseDto.getDescription());
        assertEquals(hotel.getAddress().toString(), hotelShortResponseDto.getAddress());
        assertEquals(hotel.getContacts().getPhone(), hotelShortResponseDto.getPhone());
    }

    @Test
    @DisplayName("hotelToHotelResponseDto method")
    void shouldMapHotelToHotelResponseDtoCorrectly() {
        Hotel hotel = getHotel();
        hotel.setId(ID);
        hotel.setAmenities(AMENITIES);

        HotelResponseDto hotelResponseDto = hotelMapper.hotelToHotelResponseDto(hotel);

        assertEquals(hotel.getId(), hotelResponseDto.getId());
        assertEquals(hotel.getName(), hotelResponseDto.getName());
        assertEquals(hotel.getBrand(), hotelResponseDto.getBrand());
        assertEquals(hotel.getAddress().getHouseNumber(), hotelResponseDto.getAddress().getHouseNumber());
        assertEquals(hotel.getAddress().getStreet(), hotelResponseDto.getAddress().getStreet());
        assertEquals(hotel.getAddress().getCity(), hotelResponseDto.getAddress().getCity());
        assertEquals(hotel.getAddress().getCountry(), hotelResponseDto.getAddress().getCountry());
        assertEquals(hotel.getAddress().getPostCode(), hotelResponseDto.getAddress().getPostCode());
        assertEquals(hotel.getContacts().getPhone(), hotelResponseDto.getContacts().getPhone());
        assertEquals(hotel.getContacts().getEmail(), hotelResponseDto.getContacts().getEmail());
        assertEquals(hotel.getArrivalTime().getCheckIn(), hotelResponseDto.getArrivalTime().getCheckIn());
        assertEquals(hotel.getArrivalTime().getCheckOut(), hotelResponseDto.getArrivalTime().getCheckOut());
        assertEquals(hotel.getAmenities().size(), hotelResponseDto.getAmenities().size());
    }
}