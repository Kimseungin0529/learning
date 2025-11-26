package project.learning.jpa.공유값.임베디드타입;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity @Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    @Embedded
    private Address address;

    public User(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
