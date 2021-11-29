package epam.jwd.online_training.logic;

import epam.jwd.online_training.entity.Course;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface CourseService extends Service {

    List<Course> getAvailableCourses (int userId) throws ServiceException;
    List<Course> getTakenCourses (int userId) throws ServiceException ;
    List<Course> getAllCourses() throws ServiceException ;
    List<Course> getRelatedCourses(int teacherId) throws ServiceException;
    boolean updateCourse(int courseId, int type, int teacherId, int status, String title, String description)
            throws ServiceException;
    boolean addCourse(int type, int teacherId, int status, String title, String description) throws ServiceException;
}
