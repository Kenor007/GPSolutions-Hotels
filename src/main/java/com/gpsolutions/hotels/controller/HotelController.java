package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.dto.*;
import com.gpsolutions.hotels.error_handling.exception.ExceptionResponse;
import com.gpsolutions.hotels.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "Create a hotel by params",
            description = "Returns short information about the hotel, if the hotel has been created")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = HotelShortResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - The hotel already exists", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelShortResponseDto createHotel(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Hotel to add", content =
            @Content(schema = @Schema(implementation = HotelCreateRequestDto.class)))
            @Valid @RequestBody HotelCreateRequestDto hotelCreateRequestDto) {
        log.debug("Creating hotel: {}", hotelCreateRequestDto);
        return hotelService.createHotel(hotelCreateRequestDto);
    }

    @Operation(summary = "Add a list of amenities to a hotel",
            description = "Returns No Content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - Successfully added list of amenities"),
            @ApiResponse(responseCode = "404", description = "Not found - The hotel was not found for adding amenities", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/hotels/{id}/amenities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addAmenitiesToHotel(
            @PathVariable("id") @Positive(message = POSITIVE_ID)
            @Parameter(name = "id", description = "Hotel id", example = "1") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "List of amenities", content =
            @Content(mediaType = "application/json", schema =
            @Schema(example = "[\"Free parking\", \"Free WiFi\", \"Non-smoking rooms\"]", requiredMode = Schema.RequiredMode.REQUIRED)))
            @RequestBody Set<String> amenities) {
        log.debug("Adding amenities {} to the hotel with id {}", amenities, id);
        hotelService.addAmenitiesToHotel(id, amenities);
    }

    @Operation(summary = "Get extended information about the hotel by id",
            description = "Returns an extended information about the hotel as per the id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HotelResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Not found - The hotel was not found", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class)))
            }
    )
    @GetMapping("/hotels/{id}")
    public HotelResponseDto findHotelById(
            @PathVariable("id") @Positive(message = POSITIVE_ID)
            @Parameter(name = "id", description = "Hotel id", example = "1") Long id) {
        log.debug("Getting hotel by id: {}", id);
        return hotelService.findHotelById(id);
    }

    @Operation(summary = "Get a list of all hotels with their short information",
            description = "Returns a list of all hotels with their short information")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The list has been successfully retrieved", content =
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HotelShortResponseDto.class))))
            }
    )
    @GetMapping("/hotels")
    public List<HotelShortResponseDto> findAllHotels() {
        log.debug("Getting all hotels");
        return hotelService.findAllHotels();
    }

    @Operation(summary = "Get a list of all hotels with their short information by params",
            description = "Returns a list of all hotels with their short information on the following params: " +
                    "name, brand, city, country, amenities")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The list has been successfully retrieved", content =
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = HotelShortResponseDto.class))))
            }
    )
    @GetMapping("/search")
    public List<HotelShortResponseDto> findAllHotelsByParams(
            HotelSearchRequestDto hotelSearchRequestDto) {
        log.debug("Getting all hotels by params: {}", hotelSearchRequestDto);
        return hotelService.findAllHotelsByParams(hotelSearchRequestDto);
    }

    @Operation(summary = "Get the number of hotels grouped by each value of the specified parameter",
            description = "Returns the number of hotels grouped by each value of the specified parameter: " +
                    "brand, city, country, amenities")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "The histogram has been successfully retrieved")
            }
    )
    @GetMapping("/histogram/{param}")
    public Map<String, Long> getHotelsHistogram(
            @PathVariable("param") @NotBlank(message = "param should not be blank")
            @Parameter(name = "param", description = "Histogram param", example = "city") String param) {
        log.debug("Getting hotels histogram by param: {}", param);
        return hotelService.getHistogram(param);
    }
}