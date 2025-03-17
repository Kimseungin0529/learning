package project.learning.jpa.sharedValue.real;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public CourseResponse applyCourse(Long courseId) {
        Course course = courseRepository.findByIdWithLock(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found"));
        //현재 인원 늘리기
        course.addCurrentEnrollmentCapacity();
        //courseRepository.save(course);

        return CourseResponse.builder()
                .id(course.getId())
                .build();
    }
}
