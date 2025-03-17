package project.learning.jpa.sharedValue.real;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CourseServiceTest {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;
    @PersistenceContext
    EntityManager entityManager;

    @DisplayName("해당 강좌에 대해 수강 신청합니다. - 공유 값객체 에시 실제 코드에 적용하기")
    @Test
    void test() {
        // given
        int maxCapacity = 10;
        Course course = Course.of("강좌1", EnrollmentCapacity.of(maxCapacity, 0));
        Course savedCourse = courseRepository.save(course);

        // when
        CourseResponse courseResponse = courseService.applyCourse(savedCourse.getId());
        entityManager.flush();
        entityManager.clear();
        // then
        Course findCourse = courseRepository.findById(courseResponse.getId()).get();

        EnrollmentCapacity enrollmentCapacity = findCourse.getEnrollmentCapacity();
        assertThat(enrollmentCapacity.getCurrentEnrollment()).isEqualTo(1);
    }

}