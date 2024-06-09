package com.gpsolutions.hotels.error_handling.exception;

public class HotelExistsException extends BadRequestException {
    public HotelExistsException(String message) {
        super(message);
    }
}