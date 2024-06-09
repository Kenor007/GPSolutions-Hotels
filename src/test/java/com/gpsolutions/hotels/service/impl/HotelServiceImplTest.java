package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.dto.HotelCreateRequestDto;
import com.gpsolutions.hotels.dto.HotelResponseDto;
import com.gpsolutions.hotels.dto.HotelSearchRequestDto;
import com.gpsolutions.hotels.dto.HotelShortResponseDto;
import com.gpsolutions.hotels.error_handling.exception.HotelExistsException;
import com.gpsolutions.hotels.error_handling.exception.HotelNotFoundException;
import com.gpsolutions.hotels.mapper.HotelMapper;
import com.gpsolutions.hotels.model.Hotel;
import com.gpsolutions.hotels.repository.HotelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static com.gpsolutions.hotels.util.data.HotelTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private HotelMapper hotelMapper;
    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    @DisplayName("Return HotelShortResponseDto if hotel is created")
    void shouldReturnHotelShortResponseDtoIfHotelCreated() {
        HotelCreateRequestDto hotelCreateRequestDto = getHotelCreateRequestDto();
        Hotel hotel = getHotel();
        Hotel savedHotel = getHotel();
        savedHotel.setId(ID);
        HotelShortResponseDto hotelShortResponseDto = getHotelShortResponseDto();

        when(hotelMapper.hotelCreateRequestDtoToHotel(hotelCreateRequestDto)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(savedHotel);
        when(hotelMapper.hotelToHotelShortResponseDto(savedHotel)).thenReturn(hotelShortResponseDto);

        HotelShortResponseDto result = hotelService.createHotel(hotelCreateRequestDto);

        assertNotNull(result);
        assertEquals(hotelShortResponseDto, result);
        verify(hotelMapper, times(1)).hotelCreateRequestDtoToHotel(hotelCreateRequestDto);
        verify(hotelRepository, times(1)).save(hotel);
        verify(hotelMapper, times(1)).hotelToHotelShortResponseDto(savedHotel);
    }

    @Test
    @DisplayName("Return HotelExistsException if hotel already exists")
    void shouldReturnHotelExistsExceptionIfHotelAlreadyExists() {
        HotelCreateRequestDto hotelCreateRequestDto = getHotelCreateRequestDto();
        Hotel hotel = getHotel();

        when(hotelMapper.hotelCreateRequestDtoToHotel(hotelCreateRequestDto)).thenReturn(hotel);
        Mockito.when(hotelRepository.findHotel(hotel)).thenReturn(Optional.of(hotel));

        assertThrows(HotelExistsException.class, () -> hotelService.createHotel(hotelCreateRequestDto));
        verify(hotelMapper, times(1)).hotelCreateRequestDtoToHotel(hotelCreateRequestDto);
        verify(hotelRepository, never()).save(any(Hotel.class));
    }

    @Test
    @DisplayName("Should add amenities to the hotel")
    void shouldAddAmenitiesToHotelCorrectly() {
        Set<String> amenities = new HashSet<>();
        Hotel hotel = getHotel();
        hotel.setId(ID);
        hotel.setAmenities(amenities);

        when(hotelRepository.findById(ID)).thenReturn(Optional.of(hotel));

        hotelService.addAmenitiesToHotel(ID, AMENITIES);

        verify(hotelRepository, times(1)).findById(ID);
        verify(hotelRepository, times(1)).save(hotel);
        assertEquals(AMENITIES, hotel.getAmenities());
    }

    @Test
    @DisplayName("Return HotelNotFoundException if hotel not found")
    void shouldReturnHotelNotFoundExceptionIfHotelNotFound() {
        Set<String> amenities = new HashSet<>();

        when(hotelRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.addAmenitiesToHotel(ID, amenities));
    }

    @Test
    @DisplayName("Return HotelResponseDto if hotel found")
    void shouldReturnHotelResponseDtoIfHotelFoundById() {
        Hotel hotel = getHotel();
        hotel.setId(ID);
        hotel.setAmenities(AMENITIES);
        HotelResponseDto hotelResponseDto = getHotelResponseDto();

        when(hotelRepository.findById(ID)).thenReturn(Optional.of(hotel));
        when(hotelMapper.hotelToHotelResponseDto(hotel)).thenReturn(hotelResponseDto);

        HotelResponseDto result = hotelService.findHotelById(ID);

        assertEquals(hotelResponseDto, result);
        verify(hotelRepository, times(1)).findById(ID);
        verify(hotelMapper, times(1)).hotelToHotelResponseDto(hotel);
    }

    @Test
    @DisplayName("Return empty list of hotels")
    void shouldReturnEmptyListOfHotels() {
        when(hotelRepository.findAll()).thenReturn(Collections.emptyList());

        List<HotelShortResponseDto> result = hotelService.findAllHotels();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Return list of hotels")
    void shouldReturnListHotelShortResponseDtoOfAllHotels() {
        Hotel hotel1 = getHotel();
        hotel1.setId(1L);
        Hotel hotel2 = getHotel();
        hotel2.setId(2L);
        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);
        HotelShortResponseDto hotelShortResponseDto1 = getHotelShortResponseDto();
        hotelShortResponseDto1.setId(1L);
        HotelShortResponseDto hotelShortResponseDto2 = getHotelShortResponseDto();
        hotelShortResponseDto2.setId(2L);
        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.hotelToHotelShortResponseDto(hotel1)).thenReturn(hotelShortResponseDto1);
        when(hotelMapper.hotelToHotelShortResponseDto(hotel2)).thenReturn(hotelShortResponseDto2);

        List<HotelShortResponseDto> result = hotelService.findAllHotels();

        assertEquals(2, result.size());
        assertEquals(hotelShortResponseDto1, result.get(0));
        assertEquals(hotelShortResponseDto2, result.get(1));
    }

    @Test
    @DisplayName("Return list HotelShortResponseDto of all hotels by params")
    void shouldReturnAllHotelsByParamsIfParamsValid() {
        HotelSearchRequestDto hotelSearchRequestDto = getHotelSearchRequestDto();
        Hotel hotel1 = getHotel();
        Hotel hotel2 = getHotel();
        hotel2.setId(2L);
        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);
        HotelShortResponseDto hotelShortResponseDto1 = getHotelShortResponseDto();
        HotelShortResponseDto hotelShortResponseDto2 = getHotelShortResponseDto();
        hotelShortResponseDto2.setId(2L);

        when(hotelRepository.findAllHotelsByParams(hotelSearchRequestDto)).thenReturn(hotels);
        when(hotelMapper.hotelToHotelShortResponseDto(hotel1)).thenReturn(hotelShortResponseDto1);
        when(hotelMapper.hotelToHotelShortResponseDto(hotel2)).thenReturn(hotelShortResponseDto2);

        List<HotelShortResponseDto> result = hotelService.findAllHotelsByParams(hotelSearchRequestDto);

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(hotelShortResponseDto1, result.get(0));
        assertEquals(hotelShortResponseDto2, result.get(1));
    }

    @Test
    @DisplayName("Return map histogram by brand")
    void shouldReturnHistogramWhenParamIsBrand() {
        Hotel hotel1 = getHotel();
        Hotel hotel2 = getHotel();
        hotel2.setId(2L);
        List<Hotel> hotels = List.of(hotel1, hotel2);
        Map<String, Long> histogram = hotels.stream()
                .collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));

        when(hotelRepository.findAll()).thenReturn(hotels);

        Map<String, Long> result = hotelService.getHistogram("brand");

        assertEquals(histogram, result);
    }

    @Test
    @DisplayName("Return IllegalArgumentException if param does not exist")
    void shouldThrowIllegalArgumentExceptionIfParamForHistogramNotExist() {
        Hotel hotel1 = getHotel();
        Hotel hotel2 = getHotel();
        hotel2.setId(2L);
        List<Hotel> hotels = List.of(hotel1, hotel2);
        when(hotelRepository.findAll()).thenReturn(hotels);

        assertThrows(IllegalArgumentException.class, () -> hotelService.getHistogram("unknown"));
    }
}