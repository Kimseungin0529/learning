package project.learning.jpa.sharedValue.real;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class EnrollmentCapacity {

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private int currentEnrollment;

    private EnrollmentCapacity(int maxCapacity, int currentEnrollment) {
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = currentEnrollment;
    }

    public static EnrollmentCapacity of(int maxCapacity, int currentEnrollment) {
        return new EnrollmentCapacity(maxCapacity, currentEnrollment);
    }

    public void addCurrentCapacity() {
        if (currentEnrollment >= maxCapacity) {
            throw new IllegalArgumentException("인원이 꽉 찼습니다.");
        }
        currentEnrollment++;
    }

}
