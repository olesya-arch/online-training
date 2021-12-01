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
            "select c.id_course, ct.category, c.teacher_id, cs.course_status, c.is_available, c.c_title, c.c_description, " +
                    "u_a.id_account, u_a.e_mail, u_a.u_password, u_a.first_name, u_a.last_name, " +
                    "r.account_role, u_s.u_status " +
                    "from course as c " +
                    "inner join course_type ct on c.course_type = ct.id_type " +
                    "inner join user_account u_a on c.teacher_id = u_a.id_account " +
                    "inner join course_status cs on c.course_status = cs.id_c_status " +
                    "inner join role r on u_a.account_role = r.id_role " +
                    "inner join user_status u_s on status_id = id_status " +
                    "where c.teacher_id=?";

    private static final String FIND_TAKEN_COURSES_AND_RELATED_DATA =
            "select c.id_course, ct.category, c.teacher_id, cs.course_status, c.is_available, c.c_title, c.c_description, " +
                    "u_a.id_account, u_a.e_mail, u_a.u_password, u_a.first_name, u_a.last_name, " +
                    "r.account_role, u_s.u_status, c_e.course_id, c_e.student_id " +
                    "from online_training_db.course as c " +
                    "inner join course_type ct on c.course_type = ct.id_type " +
                    "inner join user_account u_a on c.teacher_id = u_a.id_account " +
                    "inner join course_status cs on c.course_status = cs.id_c_status " +
                    "inner join role r on u_a.account_role = r.id_role " +
                    "inner join user_status u_s on status_id = id_status " +
                    "inner join course_enrolment c_e on c.id_course = c_e.course_id " +
                    "where c_e.student_id=?";

    private static final String FIND_AVAILABLE_COURSES_AND_RELATED_DATA =
            "select c.id_course, ct.category, c.teacher_id, cs.course_status, c.is_available, c.c_title, c.c_description, " +
                    "u_a.id_account, u_a.e_mail, u_a.u_password, u_a.first_name, u_a.last_name, " +
                    "r.account_role, u_s.u_status " +
                    "from online_training_db.course as c " +
                    "inner join course_type ct on c.course_type = ct.id_type " +
                    "inner join user_account u_a on c.teacher_id = u_a.id_account " +
                    "inner join course_status cs on c.course_status = cs.id_c_status " +
                    "inner join role r on u_a.account_role = r.id_role " +
                    "inner join user_status u_s on status_id = id_status " +
                    "where is_available = 1 and c.id_course not in " +
                    "(select course_id from course_enrolment where student_id=?)";

    private static final String FIND_ALL_COURSES_AND_RELATED_DATA =
            "select c.id_course, ct.category, c.teacher_id, cs.course_status, c.is_available, c.c_title, c.c_description, " +
                    "u_a.id_account, u_a.e_mail, u_a.u_password, u_a.first_name, u_a.last_name, " +
                    "r.account_role, u_s.u_status " +
                    "from online_training_db.course as c " +
                    "inner join course_type ct on c.course_type = ct.id_type " +
                    "inner join user_account u_a on c.teacher_id = u_a.id_account " +
                    "inner join course_status cs on c.course_status = cs.id_c_status " +
                    "inner join role r on u_a.account_role = r.id_role " +
                    "inner join user_status u_s on status_id = id_status";

    private static final String UPDATE_COURSE_BY_ID =
            "update course " +
                    "set course_type=?, " +
                    "teacher_id=?, " +
                    "course_status=?, " +
                    "is_available=?, " +
                    "c_title=?, " +
                    "c_description=? " +
                    "where id_course=?";

    private static final String INSERT_NEW_COURSE =
            "insert into course "+
                    "(course_type, " +
                    "teacher_id, " +
                    "course_status, " +
                    "is_available, " +
                    "c_title, " +
                    "c_description) " +
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
    public boolean updateCourseById(int courseId, int type, int teacherId, int status, int isAvailable,
                                    String title, String description) throws DaoException {

        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_COURSE_BY_ID)) {
            if (type == 0) {
                statement.setNull(1, Types.NULL);
            } else {
                statement.setInt(1, type);
            }
            if (teacherId == 0) {
                statement.setNull(2, Types.NULL);
            } else {
                statement.setInt(2, teacherId);
            }
            statement.setInt(3, status);
            statement.setInt(4, isAvailable);
            statement.setString(5, title);
            statement.setString(6, description);
            statement.setInt(7, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(FAIL_UPDATING_NEW_COURSE, e);
            throw new DaoException(FAIL_UPDATING_NEW_COURSE, e);
        }
        return true;
    }

    @Override
    public boolean addCourse(int type, int teacherId, int status, int isAvailable, String title, String description)
            throws DaoException {
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(INSERT_NEW_COURSE)) {
            if (type == 0) {
                statement.setNull(1, Types.NULL);
            } else {
            statement.setInt(1, type);
            }
            if (teacherId == 0) {
                statement.setNull(2, Types.NULL);
            } else {
            statement.setInt(2, teacherId);
            }
            statement.setInt(3, status);
            statement.setInt(4, isAvailable);
            statement.setString(5, title);
            statement.setString(6, description);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(FAIL_INSERTING_NEW_COURSE, e);
            throw new DaoException(FAIL_INSERTING_NEW_COURSE, e);
        }
        return true;
    }
}
