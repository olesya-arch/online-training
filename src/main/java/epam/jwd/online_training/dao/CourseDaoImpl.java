package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ProxyConnection;
import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl extends AbstractDao implements CourseDao{

    private static final Logger LOG = LogManager.getLogger(CourseDaoImpl.class);
    private static final String FIND_AVAILABLE_COURSES_EXCEPTION = "Exception in process of finding available courses in DAO. ";
    private static final String FIND_TAKEN_COURSES_EXCEPTION = "Exception in process of finding taken courses in DAO. ";
    private static final String FIND_ALL_COURSES_EXCEPTION = "Exception in process of finding all courses in DAO. ";
    private static final String FIND_RELATED_COURSES_TO_TEACHER_EXCEPTION =
            "Exception in process of finding related courses to teacher in DAO. ";
    private static final String FAIL_UPDATING_NEW_COURSE = "Fail updating new course by id in DAO. ";
    private static final String FAIL_INSERTING_NEW_COURSE = "Fail inserting new course in DAO. ";

    private static final String FIND_RELATED_COURSES =
            "select c.id_course, " +
                    "c.c_title, " +
                    "c.c_description, " +
                    "c.course_type, " +
                    "c.teacher_id, " +
                    "c.course_status, " +
                    "c.is_available, " +
                    "u_a.id_account, " +
                    "u_a.e_mail, " +
                    "u_a.u_password, " +
                    "u_a.first_name, " +
                    "u_a.last_name, " +
                    "u_a.account_role, " +
                    "u_a.status_is_deleted " +
                    "from course as c " +
                    "inner join course_type as c_t on c.course_type = c_t.id_type " +
                    "inner join user_account as u_a on c.teacher_id = u_a.id_account " +
                    "where u_a.teacher_id=?";

    private static final String FIND_TAKEN_COURSES_AND_RELATED_DATA =
            "select c.id_course, " +
                    "c.c_title, " +
                    "c.c_description, " +
                    "c.course_type, " +
                    "c.teacher_id, " +
                    "c.course_status, " +
                    "c.is_available, " +
                    "u_a.id_account, " +
                    "u_a.e_mail, " +
                    "u_a.u_password, " +
                    "u_a.first_name, " +
                    "u_a.last_name, " +
                    "u_a.account_role, " +
                    "u_a.status_is_deleted, " +
                    "c_e.course_id, c_e.student_id " +
                    "from online_training_db.course as c " +
                    "inner join course_type as ct on c.course_type = ct.id_type " +
                    "inner join user_account as u_a on c.teacher_id = u_a.id_account " +
                    "inner join course_enrolment as c_e on c.id_course = c_e.course_id " +
                    "where c_e.student_id=?";

    private static final String FIND_AVAILABLE_COURSES_AND_RELATED_DATA =
            "select c.id_course, " +
                    "c.c_title, " +
                    "c.c_description, " +
                    "c.course_type, " +
                    "c.teacher_id, " +
                    "c.course_status, " +
                    "c.is_available,  " +
                    "u_a.id_account, " +
                    "u_a.e_mail, " +
                    "u_a.u_password, " +
                    "u_a.first_name, " +
                    "u_a.last_name, " +
                    "u_a.account_role, " +
                    "u_a.status_is_deleted, " +
                    "from online_training_db.course as c " +
                    "inner join course_type as ct on c.course_type = ct.id_type " +
                    "inner join user_account as u_a on c.teacher_id = u_a.id_account " +
                    "where c.is_available = 1 and c.id_course NOT IN " +
                    "(select course_id from course_enrolment AS taken_courses where student_id=?)";

    private static final String FIND_ALL_COURSES_AND_RELATED_DATA =
            "select c.id_course, " +
                    "c.c_title, " +
                    "c.c_description, " +
                    "c.course_type, " +
                    "c.teacher_id, " +
                    "c.course_status, " +
                    "c.is_available,  " +
                    "u_a.id_account, " +
                    "u_a.e_mail, " +
                    "u_a.u_password, " +
                    "u_a.first_name, " +
                    "u_a.last_name, " +
                    "u_a.account_role, " +
                    "u_a.status_is_deleted " +
                    "from online_training_db.course as c " +
                    "inner join course_type as ct on c.course_type = ct.id_type " +
                    "inner join user_account as u_a on c.teacher_id = u_a.id_account ";


    private static final String UPDATE_COURSE_BY_ID =
            "update course " +
                    "set c_title=?, " +
                    "c_description=?, " +
                    "course_type=?, " +
                    "teacher_id=?, " +
                    "course_status=?, " +
                    "is_available=?, " +
                    "where id_course=?";

    private static final String INSERT_NEW_COURSE =
            "insert into course "+
                    "(c_title, " +
                    "c_description, " +
                    "course_type, " +
                    "teacher_id, " +
                    "course_status, " +
                    "is_available) " +
                    "values (?,?,?,?,?,?)";

    @Override
    public List<CourseDto> findAvailableCourses(int userId) throws DaoException {
        List<CourseDto> availableCourses = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_AVAILABLE_COURSES_AND_RELATED_DATA)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                availableCourses.add(courseDto);
            }
        } catch (SQLException e) {
            LOG.error(FIND_AVAILABLE_COURSES_EXCEPTION, e);
            throw new DaoException(FIND_AVAILABLE_COURSES_EXCEPTION, e);
        }
        return availableCourses;
    }

    @Override
    public List<CourseDto> findTakenCourses(int userId) throws DaoException {
        List<CourseDto> takenCourses = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_TAKEN_COURSES_AND_RELATED_DATA)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                takenCourses.add(courseDto);
            }
        } catch (SQLException e) {
            LOG.error(FIND_TAKEN_COURSES_EXCEPTION, e);
            throw new DaoException(FIND_TAKEN_COURSES_EXCEPTION, e);
        }
        return takenCourses;
    }

    @Override
    public List<CourseDto> findAllCourses() throws DaoException {
        List<CourseDto> allCourses = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_ALL_COURSES_AND_RELATED_DATA)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                allCourses.add(courseDto);
            }
        } catch (SQLException e) {
            LOG.error(FIND_ALL_COURSES_EXCEPTION, e);
            throw new DaoException(FIND_ALL_COURSES_EXCEPTION, e);
        }
        return allCourses;
    }

    @Override
    public List<CourseDto> findRelatedCourses(int teacherId) throws DaoException {
        List<CourseDto> relatedCourses = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_RELATED_COURSES)) {
            statement.setInt(1, teacherId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseDto courseDto = ResultSetParser.createCourseDto(resultSet);
                relatedCourses.add(courseDto);
            }
        } catch (SQLException e) {
            LOG.error(FIND_RELATED_COURSES_TO_TEACHER_EXCEPTION, e);
            throw new DaoException(FIND_RELATED_COURSES_TO_TEACHER_EXCEPTION, e);
        }
        return relatedCourses;
    }

    @Override
    public boolean updateCourseById(int courseId, String title, String description, int type,
                                    int teacherId, String status, int isAvailable) throws DaoException {

        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_COURSE_BY_ID)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, type);
            if (teacherId == 0) {
                statement.setNull(4, Types.NULL);
            } else {
                statement.setInt(4, teacherId);
            }
            statement.setString(5, status);
            statement.setInt(6, isAvailable);
            statement.setInt(7, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(FAIL_UPDATING_NEW_COURSE, e);
            throw new DaoException(FAIL_UPDATING_NEW_COURSE, e);
        }
        return true;
    }

    @Override
    public boolean addCourse(String title, String description, int type, int teacherId, String status, int isAvailable) throws DaoException {
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(INSERT_NEW_COURSE)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, type);
            if (teacherId == 0) {
                statement.setNull(4, Types.NULL);
            } else {
            statement.setInt(4, teacherId);
            }
            statement.setString(5, status);
            statement.setInt(6, isAvailable);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(FAIL_INSERTING_NEW_COURSE, e);
            throw new DaoException(FAIL_INSERTING_NEW_COURSE, e);
        }
        return true;
    }
}
