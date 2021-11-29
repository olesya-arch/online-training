package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.Course;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CourseDaoImpl extends AbstractDao implements CourseDao{

    private static final Logger LOG = LogManager.getLogger(CourseDaoImpl.class);

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
    public List<Course> findAvailableCourses(int userId) throws DaoException {
        return null;
    }

    @Override
    public List<Course> findTakenCourses(int userId) throws DaoException {
        return null;
    }

    @Override
    public List<Course> findAllCourses() throws DaoException {
        return null;
    }

    @Override
    public List<Course> findRelatedCourses(int teacherId) throws DaoException {
        return null;
    }

    @Override
    public boolean updateCourseById(int courseId, int type, int teacherId, int status, String title, String description) throws DaoException {
        return false;
    }

    @Override
    public boolean addCourse(int type, int teacherId, int status, String title, String description) throws DaoException {
        return false;
    }
}
