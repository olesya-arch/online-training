package epam.jwd.online_training.dao;

import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface CourseDao {

    List<CourseDto> findAvailableCourses (int userId) throws DaoException;
    List<CourseDto> findTakenCourses (int userId) throws DaoException ;
    List<CourseDto> findAllCourses() throws DaoException ;
    List<CourseDto> findRelatedCourses(int teacherId) throws DaoException;
    boolean updateCourseById(int courseId, int type, int teacherId, int status, int isAvailable,
                             String title, String description) throws DaoException;
    boolean addCourse(int type, int teacherId, int status, int isAvailable, String title, String description)
            throws DaoException;
}
