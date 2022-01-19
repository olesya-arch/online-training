package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.entity.*;
import epam.jwd.online_training.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseServiceImplTest {

    private CourseServiceImpl service;
    private Course course;
    private CourseDto courseDto;
    private int userId;
    private int courseId;
    private String title;
    private String description;
    private int typeId;
    private int teacherId;
    private String status;
    private int isAvailable;
    List<CourseDto> courseDtos;

    @Before
    public void setUp() throws Exception {
        service = mock(CourseServiceImpl.class);
        course = new Course(2, "English for beginners",
                "This course for those who begins to study English.",
                12, 15, CourseStatus.IN_PROCESS, true);
        courseDto = new CourseDto(course,
                new CourseType(1, Language.ENGLISH),
                new User(1, "sar555@gmail.com", "sar555",
                        "Sarah", "White", UserRole.TEACHER));
        userId = 100;
        courseId = 2;
        title = "English for beginners";
        description = "This course for those who begins to study English.";
        typeId = 12;
        teacherId = 15;
        status = "IN_PROCESS";
        isAvailable = 1;
        courseDtos = new ArrayList<>();
        courseDtos.add(courseDto);
    }

    @Test
    public void getAvailableCourses() throws ServiceException {
        List<CourseDto> expectedResult = courseDtos;
        when(service.getAvailableCourses(userId)).thenReturn(expectedResult);
        List<CourseDto> actualResult = service.getAvailableCourses(userId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getTakenCourses() throws ServiceException {
        List<CourseDto> expectedResult = courseDtos;
        when(service.getTakenCourses(userId)).thenReturn(expectedResult);
        List<CourseDto> actualResult = service.getTakenCourses(userId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getAllCourses() throws ServiceException {
        List<CourseDto> expectedResult = courseDtos;
        when(service.getAllCourses()).thenReturn(expectedResult);
        List<CourseDto> actualResult = service.getAllCourses();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getRelatedCourses() throws ServiceException {
        List<CourseDto> expectedResult = courseDtos;
        when(service.getRelatedCourses(teacherId)).thenReturn(expectedResult);
        List<CourseDto> actualResult = service.getRelatedCourses(teacherId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void updateCourse() throws ServiceException {
        boolean expectedResult = true;
        when(service.updateCourse(courseId, title, description, typeId, teacherId, status, isAvailable)).thenReturn(expectedResult);
        boolean actualResult = service.updateCourse(courseId, title, description, typeId, teacherId, status, isAvailable);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void addCourse() throws ServiceException {
        boolean expectedResult = true;
        when(service.addCourse(title, description, typeId, teacherId, status, isAvailable)).thenReturn(expectedResult);
        boolean actualResult = service.addCourse(title, description, typeId, teacherId, status, isAvailable);
        assertEquals(actualResult, expectedResult);
    }
}