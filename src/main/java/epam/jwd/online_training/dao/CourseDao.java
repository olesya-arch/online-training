package epam.jwd.online_training.dao;

import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface CourseDao {

    List<CourseDto> findAvailableCourses (int userId) throws DaoException;
    List<CourseDto> findTakenCourses (int userId) throws DaoException ;
    List<CourseDto> findAllCourses() throws DaoException ;
    List<CourseDto> findRelatedCourses(int teacherId) throws DaoException;
    boolean updateCourseById(int courseId, String title, String description, int typeId, int teacherId,
                             String status, int isAvailable) throws DaoException;
    boolean addCourse(String title, String description, int typeId, int teacherId, String status, int isAvailable)
            throws DaoException;
}
