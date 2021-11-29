package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.Course;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface CourseDao {

    List<Course> findAvailableCourses (int userId) throws DaoException;
    List<Course> findTakenCourses (int userId) throws DaoException ;
    List<Course> findAllCourses() throws DaoException ;
    List<Course> findRelatedCourses(int teacherId) throws DaoException;
    boolean updateCourseById(int courseId, int type, int teacherId, int status, String title, String description)
            throws DaoException;
    boolean addCourse(int type, int teacherId, int status, String title, String description) throws DaoException;
}
