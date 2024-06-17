package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.dto.*;
import com.gpsolutions.hotels.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.gpsolutions.hotels.error_handling.constant.ExceptionAnswer.POSITIVE_ID;

@Tag(name = "Hotel Controller", description = "API for working with hotels")
@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
@Validated
@Slf4j
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a hotel by params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request - The hotel already exists"),
    })
    public HotelShortResponseDto createHotel(
            @Valid @RequestBody HotelCreateRequestDto hotelCreateRequestDto) {
        log.debug("Creating hotel: {}", hotelCreateRequestDto);
        return hotelService.createHotel(hotelCreateRequestDto);
    }

    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Add a list of amenities to a hotel")
    public void addAmenitiesToHotel(
            @PathVariable("id") @Positive(message = POSITIVE_ID) Long id,
            @RequestBody Set<String> amenities) {
        log.debug("Adding amenities {} to the hotel with id {}", amenities, id);
        hotelService.addAmenitiesToHotel(id, amenities);
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Get extended information about the hotel by id")
    public HotelResponseDto findHotelById(
            @PathVariable("id") @Positive(message = POSITIVE_ID) Long id) {
        log.debug("Getting hotel by id: {}", id);
        return hotelService.findHotelById(id);
    }

    @GetMapping("/hotels")
    @Operation(summary = "Get a list of all hotels with their short information")
    public List<HotelShortResponseDto> findAllHotels() {
        log.debug("Getting all hotels");
        return hotelService.findAllHotels();
    }

    @GetMapping("/search")
    @Operation(summary = "Search for a list of all hotels with their short information on the following params:" +
            " name, brand, city, country, amenities")
    public List<HotelShortResponseDto> findAllHotelsByParams(
            HotelSearchRequestDto hotelSearchRequestDto) {
        log.debug("Getting all hotels by params: {}", hotelSearchRequestDto);
        return hotelService.findAllHotelsByParams(hotelSearchRequestDto);
    }

    @GetMapping("/histogram/{param}")
    @Operation(summary = "Get the number of hotels grouped by each value of the specified parameter." +
            " Parameter: brand, city, country, amenities")
    public Map<String, Long> getHotelsHistogram(
            @PathVariable("param") @NotBlank(message = "param should not be blank") String param) {
        log.debug("Getting hotels histogram by param: {}", param);
        return hotelService.getHistogram(param);
    }
}