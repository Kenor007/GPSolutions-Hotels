package com.gpsolutions.hotels.mapper;

import com.gpsolutions.hotels.dto.HotelResponseDto;
import com.gpsolutions.hotels.dto.HotelCreateRequestDto;
import com.gpsolutions.hotels.dto.HotelShortResponseDto;
import com.gpsolutions.hotels.model.Hotel;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface HotelMapper {
    Hotel hotelCreateRequestDtoToHotel(HotelCreateRequestDto hotelCreateRequestDto);

    @Mappings({
            @Mapping(target = "address", expression = "java(hotel.getAddress().toString())"),
            @Mapping(target = "phone", source = "contacts.phone")
    })
    HotelShortResponseDto hotelToHotelShortResponseDto(Hotel hotel);

    HotelResponseDto hotelToHotelResponseDto(Hotel hotel);
}