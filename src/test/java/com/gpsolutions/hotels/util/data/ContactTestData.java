package com.gpsolutions.hotels.util.data;

import com.gpsolutions.hotels.dto.ContactDto;
import com.gpsolutions.hotels.model.Contact;

public final class ContactTestData {
    public static final String PHONE = "+375 17 309-80-00";
    public static final String EMAIL = "doubletreeminsk.info@hilton.com";

    private ContactTestData() {
    }

    public static Contact getContact() {
        Contact contact = new Contact();
        contact.setPhone(PHONE);
        contact.setEmail(EMAIL);
        return contact;
    }

    public static ContactDto getContactDto() {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhone(PHONE);
        contactDto.setEmail(EMAIL);
        return contactDto;
    }
}