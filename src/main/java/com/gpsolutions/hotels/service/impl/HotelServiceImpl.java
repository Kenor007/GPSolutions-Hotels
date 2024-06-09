package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.dto.*;
import com.gpsolutions.hotels.error_handling.exception.HotelExistsException;
import com.gpsolutions.hotels.error_handling.exception.HotelNotFoundException;
import com.gpsolutions.hotels.mapper.HotelMapper;
import com.gpsolutions.hotels.model.Hotel;
import com.gpsolutions.hotels.repository.HotelRepository;
import com.gpsolutions.hotels.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gpsolutions.hotels.error_handling.constant.ExceptionAnswer.HOTEL_ALREADY_EXISTS;
import static com.gpsolutions.hotels.error_handling.constant.ExceptionAnswer.HOTEL_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional
    public HotelShortResponseDto createHotel(HotelCreateRequestDto hotelCreateRequestDto) {
        Hotel hotel = hotelMapper.hotelCreateRequestDtoToHotel(hotelCreateRequestDto);
        validateHotel(hotel);
        Hotel savedHotel = hotelRepository.save(hotel);
        log.debug("Hotel with id {} is saved", savedHotel.getId());
        return hotelMapper.hotelToHotelShortResponseDto(savedHotel);
    }

    @Override
    @Transactional
    public void addAmenitiesToHotel(Long hotelId, Set<String> amenities) {
        Hotel hotel = findHotelByIdOrThrow(hotelId);
        if (amenities.isEmpty()) {
            log.debug("List of amenities is empty");
        }
        hotel.getAmenities().addAll(amenities);
        log.debug("List of amenities has been added");
        hotelRepository.save(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public HotelResponseDto findHotelById(Long hotelId) {
        Hotel hotel = findHotelByIdOrThrow(hotelId);
        log.debug("Hotel with id {} is found", hotel.getId());
        return hotelMapper.hotelToHotelResponseDto(hotel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponseDto> findAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        if (hotels.isEmpty()) {
            log.debug("List of hotels is empty");
        }
        log.debug("List of hotels has been found");
        return hotels.stream().map(hotelMapper::hotelToHotelShortResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelShortResponseDto> findAllHotelsByParams(HotelSearchRequestDto hotelSearchRequestDto) {
        if (validateHotelSearchRequestDto(hotelSearchRequestDto)) {
            List<Hotel> hotels = hotelRepository.findAllHotelsByParams(hotelSearchRequestDto);
            if (hotels.isEmpty()) {
                log.debug("List of hotels is empty");
            }
            log.debug("List of hotels has been found by params {}", hotelSearchRequestDto);
            return hotels.stream().map(hotelMapper::hotelToHotelShortResponseDto).toList();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getHistogram(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        if (hotels.isEmpty()) {
            log.debug("List of hotels is empty");
        }
        return switch (param) {
            case "brand" -> hotels.stream()
                    .collect(Collectors.groupingBy(
                            Hotel::getBrand,
                            Collectors.counting()
                    ));
            case "city" -> hotels.stream()
                    .collect(Collectors.groupingBy(
                            hotel -> hotel.getAddress().getCity(),
                            Collectors.counting()
                    ));
            case "country" -> hotels.stream()
                    .collect(Collectors.groupingBy(
                            hotel -> hotel.getAddress().getCountry(),
                            Collectors.counting()
                    ));
            case "amenities" -> hotels.stream()
                    .flatMap(hotel -> hotel.getAmenities().stream())
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));
            default -> {
                log.error("Unknown parameter: {}", param);
                throw new IllegalArgumentException("Unknown parameter: " + param);
            }
        };
    }

    private void validateHotel(Hotel hotel) {
        log.debug("Checking hotel {}", hotel);
        if (hotelRepository.findHotel(hotel).isPresent()) {
            log.error("Hotel already exists");
            throw new HotelExistsException(HOTEL_ALREADY_EXISTS);
        }
    }

    private Hotel findHotelByIdOrThrow(Long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(() -> {
            log.error("Hotel with id {} not found", hotelId);
            return new HotelNotFoundException(String.format(HOTEL_NOT_FOUND, hotelId));
        });
    }

    private boolean validateHotelSearchRequestDto(HotelSearchRequestDto hotelSearchRequestDto) {
        return Objects.nonNull(hotelSearchRequestDto.getName())
                || Objects.nonNull(hotelSearchRequestDto.getBrand())
                || Objects.nonNull(hotelSearchRequestDto.getCity())
                || Objects.nonNull(hotelSearchRequestDto.getCountry())
                || Objects.nonNull(hotelSearchRequestDto.getAmenities())
                && !hotelSearchRequestDto.getAmenities().isEmpty();
    }
}