package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface CourseService extends Service {

    List<CourseDto> getAvailableCourses (int userId) throws ServiceException;
    List<CourseDto> getTakenCourses (int userId) throws ServiceException ;
    List<CourseDto> getAllCourses() throws ServiceException ;
    List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException;
    boolean updateCourse(int courseId, String title, String description, int type, int teacherId,
                         String status, int isAvailable) throws ServiceException;
    boolean addCourse(String title, String description, int type, int teacherId, String status, int isAvailable)
            throws ServiceException;
    }
