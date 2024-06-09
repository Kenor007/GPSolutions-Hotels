package com.gpsolutions.hotels.util.data;

import com.gpsolutions.hotels.dto.AddressDto;
import com.gpsolutions.hotels.model.Address;

public final class AddressTestData {
    public static final Integer HOUSE_NUMBER = 9;
    public static final String STREET = "Pobediteley Avenue";
    public static final String CITY = "Minsk";
    public static final String COUNTRY = "Belarus";
    public static final String POST_CODE = "220004";

    private AddressTestData() {
    }

    public static Address getAddress() {
        Address address = new Address();
        address.setHouseNumber(HOUSE_NUMBER);
        address.setStreet(STREET);
        address.setCity(CITY);
        address.setCountry(COUNTRY);
        address.setPostCode(POST_CODE);
        return address;
    }

    public static AddressDto getAddressDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setHouseNumber(HOUSE_NUMBER);
        addressDto.setStreet(STREET);
        addressDto.setCity(CITY);
        addressDto.setCountry(COUNTRY);
        addressDto.setPostCode(POST_CODE);
        return addressDto;
    }
}