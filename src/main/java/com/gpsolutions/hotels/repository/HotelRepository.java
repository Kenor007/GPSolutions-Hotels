package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.dto.HotelSearchRequestDto;
import com.gpsolutions.hotels.model.Address;
import com.gpsolutions.hotels.model.ArrivalTime;
import com.gpsolutions.hotels.model.Contact;
import com.gpsolutions.hotels.model.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    default Optional<Hotel> findHotel(Hotel hotel) {
        Specification<Hotel> specification = Specification.where(nameIn(hotel.getName()))
                .and(descriptionIn(hotel.getDescription()))
                .and(brandIn(hotel.getBrand()))
                .and(addressIn(hotel.getAddress()))
                .and(contactsIn(hotel.getContacts()))
                .and(arrivalTimeIn(hotel.getArrivalTime()));
        return findOne(specification);
    }

    default List<Hotel> findAllHotelsByParams(HotelSearchRequestDto hotelSearchRequest) {
        Specification<Hotel> specification = Specification.where(nameIn(hotelSearchRequest.getName()))
                .and(brandIn(hotelSearchRequest.getBrand()))
                .and(cityIn(hotelSearchRequest.getCity()))
                .and(countryIn(hotelSearchRequest.getCountry()))
                .and(amenitiesIn(hotelSearchRequest.getAmenities()));
        return findAll(specification);
    }

    default Specification<Hotel> nameIn(String name) {
        return attributeEquals("name", name);
    }

    default Specification<Hotel> descriptionIn(String description) {
        return attributeEquals("description", description);
    }

    default Specification<Hotel> brandIn(String brand) {
        return attributeEquals("brand", brand);
    }

    default Specification<Hotel> addressIn(Address address) {
        return (hotel, cq, cb) -> Objects.nonNull(address)
                ? cb.equal(hotel.get("address"), address) : cb.conjunction();
    }

    default Specification<Hotel> contactsIn(Contact contacts) {
        return (hotel, cq, cb) -> Objects.nonNull(contacts)
                ? cb.equal(hotel.get("contacts"), contacts) : cb.conjunction();
    }

    default Specification<Hotel> arrivalTimeIn(ArrivalTime arrivalTime) {
        return (hotel, cq, cb) -> Objects.nonNull(arrivalTime)
                ? cb.equal(hotel.get("arrivalTime"), arrivalTime) : cb.conjunction();
    }

    default Specification<Hotel> cityIn(String city) {
        return (hotel, cq, cb) -> isNotNull(city)
                ? cb.equal(cb.lower(hotel.get("address").get("city")), city.toLowerCase()) : cb.conjunction();
    }

    default Specification<Hotel> countryIn(String country) {
        return (hotel, cq, cb) -> isNotNull(country)
                ? cb.equal(cb.lower(hotel.get("address").get("country")), country.toLowerCase()) : cb.conjunction();
    }

    default Specification<Hotel> amenitiesIn(Set<String> amenities) {
        return (hotel, cq, cb) -> {
            if (isNotEmpty(amenities)) {
                List<Predicate> predicates = new ArrayList<>();
                for (String amenity : amenities) {
                    Subquery<Long> sq = cq.subquery(Long.class);
                    Root<Hotel> hotelRoot = sq.correlate(hotel);
                    Join<Hotel, String> amenitiesJoin = hotelRoot.join("amenities");
                    sq.select(hotelRoot.get("id"))
                            .where(cb.equal(cb.lower(amenitiesJoin), amenity.toLowerCase()));
                    predicates.add(cb.exists(sq));
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            } else {
                return cb.conjunction();
            }
        };
    }

    private static Specification<Hotel> attributeEquals(String attributeName, String value) {
        return (hotel, cq, cb) -> isNotNull(value)
                ? cb.equal(cb.lower(hotel.get(attributeName)), value.toLowerCase()) : cb.conjunction();
    }

    private static boolean isNotNull(String line) {
        return Objects.nonNull(line);
    }

    private static boolean isNotEmpty(Collection<?> collection) {
        return Objects.nonNull(collection) && !collection.isEmpty();
    }
}