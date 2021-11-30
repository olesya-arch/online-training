package epam.jwd.online_training.dao;

import epam.jwd.online_training.constant.EntityAttribute;
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

    static User createUser(ResultSet resultSet) throws DaoException {
        User user = null;
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
            String comment = resultSet.getString(EntityAttribute.TASK_REVIEW_COMMENT);
            int mark = resultSet.getInt(EntityAttribute.TASK_REVIEW_MARK);

            taskReview = new TaskReview(studentId, taskId, comment, mark);

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
}
