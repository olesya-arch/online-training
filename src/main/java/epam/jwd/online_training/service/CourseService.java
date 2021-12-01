package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface CourseService extends Service {

    List<CourseDto> getAvailableCourses (int userId) throws ServiceException;
    List<CourseDto> getTakenCourses (int userId) throws ServiceException ;
    List<CourseDto> getAllCourses() throws ServiceException ;
    List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException;
    boolean updateCourse(int courseId, int type, int teacherId, int status, int isAvailable,
                         String title, String description) throws ServiceException;
    boolean addCourse(int type, int teacherId, int status, int isAvailable,
                      String title, String description) throws ServiceException;
}
