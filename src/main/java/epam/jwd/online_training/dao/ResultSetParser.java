package epam.jwd.online_training.dao;

import epam.jwd.online_training.constant.EntityAttribute;
import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.dto.TaskDto;
import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.entity.*;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetParser {

    private static final Logger LOG = LogManager.getLogger(ResultSetParser.class);
    private static final String CANNOT_CREATE_USER = "Cannot create a user! ";
    private static final String CANNOT_CREATE_REVIEW = "Cannot create a review. ";
    private static final String CANNOT_CREATE_TASK = "Cannot create a task. ";
    private static final String CANNOT_CREATE_COURSE = "Cannot create a course. ";
    private static final String CANNOT_CREATE_COURSE_TYPE = "Cannot create a course type. ";
    private static final String FAIL_CREATING_COURSE_DTO = "Fail creating course DTO while parsing. ";

    static User createUser(ResultSet resultSet) throws DaoException {
        User user;
        try {
            Integer id = resultSet.getInt(EntityAttribute.USER_ID);
            String email = resultSet.getString(EntityAttribute.USER_EMAIL);
            String password = resultSet.getString(EntityAttribute.USER_PASSWORD);
            String firstName = resultSet.getString(EntityAttribute.USER_FIRST_NAME);
            String lastName = resultSet.getString(EntityAttribute.USER_LAST_NAME);
            String roleName = resultSet.getString(EntityAttribute.USER_ROLE);
            String roleValue = roleName.toUpperCase();
            UserRole role = UserRole.valueOf(roleValue);

            user = new User(id, email, password, firstName, lastName, role);

        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_USER, e);
            throw new DaoException(CANNOT_CREATE_USER, e);
        }
        return user;
    }

    static TaskReviewDto createTaskReviewUsersDto(ResultSet resultSet) throws DaoException {
        TaskReview taskReview = createTaskReview(resultSet);
        User student = createUser(resultSet);

        return new TaskReviewDto(taskReview, student);
    }

    private static TaskReview createTaskReview(ResultSet resultSet)throws DaoException {
        TaskReview taskReview = null;
        try {
            Integer studentId = resultSet.getInt(EntityAttribute.TASK_REVIEW_STUDENT_ID);
            Integer taskId = resultSet.getInt(EntityAttribute.TASK_REVIEW_TASK_ID);
            String answer = resultSet.getString(EntityAttribute.TASK_REVIEW_ANSWER);
            String comment = resultSet.getString(EntityAttribute.TASK_REVIEW_COMMENT);
            int mark = resultSet.getInt(EntityAttribute.TASK_REVIEW_MARK);

            taskReview = new TaskReview(studentId, taskId, answer, comment, mark);

        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_REVIEW, e);
            throw new DaoException(CANNOT_CREATE_REVIEW, e);
        }
        return taskReview;
    }

    static Task createTask(ResultSet resultSet) throws DaoException {
        Task task = null;
        try {
            Integer id = resultSet.getInt(EntityAttribute.TASK_ID);
            String title = resultSet.getString(EntityAttribute.TASK_TITLE);
            String description = resultSet.getString(EntityAttribute.TASK_DESCRIPTION);
            int courseId = resultSet.getInt(EntityAttribute.TASK_COURSE_ID);

            task = new Task(id, title, description, courseId);
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_TASK, e);
            throw new DaoException(CANNOT_CREATE_TASK, e);
        }
        return task;
    }

    static TaskDto createTaskDto(ResultSet resultSet) throws DaoException {
        Task task = createTask(resultSet);
        TaskReview taskReview = createTaskReview(resultSet);
        return new TaskDto(task, taskReview);
    }

    static Course createCourse(ResultSet resultSet) throws DaoException {
        Course course = null;
        try {
            Integer id = resultSet.getInt(EntityAttribute.COURSE_ID);
            String title = resultSet.getString(EntityAttribute.COURSE_TITLE);
            String description = resultSet.getString(EntityAttribute.COURSE_DESCRIPTION);
            int typeId = resultSet.getInt(EntityAttribute.COURSE_TYPE);
            int teacherId = resultSet.getInt(EntityAttribute.COURSE_TEACHER_ID);
            String statusLine = resultSet.getString(EntityAttribute.COURSE_STATUS);
            String statusValue = statusLine.toUpperCase();
            CourseStatus courseStatus = CourseStatus.valueOf(statusValue);
            Boolean isAvailable = resultSet.getBoolean(EntityAttribute.COURSE_IS_AVAILABLE);

            course = new Course(id, title, description, typeId, teacherId, courseStatus, isAvailable);

        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_COURSE, e);
            throw new DaoException(CANNOT_CREATE_COURSE, e);
        }
        return course;
    }

    static CourseDto createCourseDto(ResultSet resultSet) throws DaoException {
        Course course = createCourse(resultSet);
        CourseType courseType = null;
        User teacher = null;
        try {
            if (resultSet.getInt(EntityAttribute.COURSE_TYPE) != 0) {
                courseType = createCourseType(resultSet);
            }
            if (resultSet.getInt(EntityAttribute.USER_ID) != 0) {
                teacher = createUser(resultSet);
            }
        } catch (SQLException e) {
            LOG.error(FAIL_CREATING_COURSE_DTO, e);
            throw new DaoException(FAIL_CREATING_COURSE_DTO, e);
        }
        return new CourseDto(course, courseType, teacher);
    }

    static CourseType createCourseType(ResultSet resultSet) throws DaoException {
        CourseType courseType = null;
        try {
            int id = resultSet.getInt(EntityAttribute.COURSE_TYPE_ID);
            String typeLine = resultSet.getString(EntityAttribute.COURSE_TYPE_CATEGORY);
            String typeValue = typeLine.toUpperCase();
            Language language = Language.valueOf(typeValue);
            courseType = new CourseType(id, language);
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_COURSE_TYPE, e);
            throw new DaoException(CANNOT_CREATE_COURSE_TYPE, e);
        }
        return courseType;
    }
}
