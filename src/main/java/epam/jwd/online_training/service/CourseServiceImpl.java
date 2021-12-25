package epam.jwd.online_training.service;

import epam.jwd.online_training.dao.CourseDaoImpl;
import epam.jwd.online_training.dao.DAOManager;
import epam.jwd.online_training.dao.TransactionManager;
import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.DaoException;
import epam.jwd.online_training.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CourseServiceImpl implements CourseService {

    private static final Logger LOG = LogManager.getLogger(CourseServiceImpl.class);
    private static final CourseDaoImpl courseDao = DAOManager.getCourseDao();

    private static final String SHOW_AVAILABLE_COURSES_EXCEPTION = "Exception occurred showing available courses in service. ";
    private static final String SHOW_TAKEN_COURSES_EXCEPTION = "Exception occurred showing taken courses in service. ";
    private static final String SHOW_ALL_COURSES_EXCEPTION = "Exception occurred showing all courses in service. ";
    private static final String SHOW_RELATED_COURSES_TO_TEACHER_EXCEPTION =
            "Exception occurred showing related courses to teacher in service. ";
    private static final String UPDATE_COURSE_EXCEPTION = "Exception in process of updating course in service. ";
    private static final String ADD_COURSE_EXCEPTION = "Exception in process of adding course in service. ";

    @Override
    public List<CourseDto> getAvailableCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(courseDao)) {
            courseDtoList = courseDao.findAvailableCourses(userId);
        } catch (SQLException | DaoException e) {
            LOG.error(SHOW_AVAILABLE_COURSES_EXCEPTION, e);
            throw new ServiceException(SHOW_AVAILABLE_COURSES_EXCEPTION, e);
        }
        return courseDtoList;
    }

    @Override
    public List<CourseDto> getTakenCourses(int userId) throws ServiceException {
        List<CourseDto> courseDtoList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(courseDao)) {
            courseDtoList = courseDao.findTakenCourses(userId);
        } catch (SQLException | DaoException e) {
            LOG.error(SHOW_TAKEN_COURSES_EXCEPTION, e);
            throw new ServiceException(SHOW_TAKEN_COURSES_EXCEPTION, e);
        }
        return courseDtoList;
    }

    @Override
    public List<CourseDto> getAllCourses() throws ServiceException {
        List<CourseDto> courseDtoList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(courseDao)) {
            courseDtoList = courseDao.findAllCourses();
        } catch (SQLException | DaoException e) {
            LOG.error(SHOW_ALL_COURSES_EXCEPTION, e);
            throw new ServiceException(SHOW_ALL_COURSES_EXCEPTION, e);
        }
        return courseDtoList;
    }

    @Override
    public List<CourseDto> getRelatedCourses(int teacherId) throws ServiceException {
        List<CourseDto> courseDtoList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(courseDao)) {
            courseDtoList = courseDao.findRelatedCourses(teacherId);
        } catch (SQLException | DaoException e) {
            LOG.error(SHOW_RELATED_COURSES_TO_TEACHER_EXCEPTION, e);
            throw new ServiceException(SHOW_RELATED_COURSES_TO_TEACHER_EXCEPTION, e);
        }
        return courseDtoList;
    }

    @Override
    public boolean updateCourse(int courseId, String title, String description, int type,
                                int teacherId, String status, int isAvailable) throws ServiceException {
        try(TransactionManager tm = TransactionManager.launchTransaction(courseDao)) {
            courseDao.updateCourseById(courseId, title, description, type, teacherId, status, isAvailable);
        } catch (SQLException | DaoException e) {
            LOG.error(UPDATE_COURSE_EXCEPTION, e);
            throw new ServiceException(UPDATE_COURSE_EXCEPTION, e);
        }
        return true;
    }

    @Override
    public boolean addCourse(String title, String description, int type, int teacherId, String status, int isAvailable)
            throws ServiceException {
        try(TransactionManager tm = TransactionManager.launchTransaction(courseDao)) {
            courseDao.addCourse(title, description, type, teacherId, status, isAvailable);
        } catch (SQLException | DaoException e) {
            LOG.error(ADD_COURSE_EXCEPTION, e);
            throw new ServiceException(ADD_COURSE_EXCEPTION, e);
        }
        return true;
    }
}
