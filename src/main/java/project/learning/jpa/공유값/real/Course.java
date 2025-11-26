package project.learning.jpa.공유값.real;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private EnrollmentCapacity enrollmentCapacity;

    public static Course of(String name, EnrollmentCapacity enrollmentCapacity) {
        return new Course(name, enrollmentCapacity);
    }

    private Course(String name, EnrollmentCapacity enrollmentCapacity) {
        this.name = name;
        this.enrollmentCapacity = enrollmentCapacity;
    }

    public void addCurrentEnrollmentCapacity() {
        enrollmentCapacity.addCurrentCapacity();

        this.enrollmentCapacity = EnrollmentCapacity.of(enrollmentCapacity.getMaxCapacity()
                , enrollmentCapacity.getCurrentEnrollment());;
    }
}
