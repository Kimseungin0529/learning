package project.learning.jpa.공유값.임베디드타입;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable @Getter
@NoArgsConstructor
public class Address {
    private String street;
    private String city;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }
    public void updateAddress(String street) {
        this.street = street;
    }
}
