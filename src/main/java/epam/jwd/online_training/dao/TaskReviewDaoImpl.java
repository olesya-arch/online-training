package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ProxyConnection;
import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskReviewDaoImpl extends AbstractDao implements TaskReviewDao {

    private static final Logger LOG = LogManager.getLogger(TaskReviewDaoImpl.class);
    private static final String FAIL_SENDING_TASK_ANSWER = "Fail sending an answer in DAO. ";
    private static final String FAIL_SENDING_TASK_REVIEW = "Fail sending review in DAO. ";
    private static final String FAIL_FINDING_TASK_REVIEWS_BY_TASK_ID = "Fail finding reviews and users by task id in DAO. ";

    private static final String UPDATE_ANSWER =
            "update task_review " +
                    "set task_answer=? " +
                    "where student_id=? " +
                    "and t_task_id=?";

    private static final String SEND_TASK_REVIEW =
            "update task_review " +
                    "set mark=?, " +
                    "teacher_comment=? " +
                    "where student_id=? " +
                    "and t_task_id=?";

    private static final String FIND_TASK_REVIEWS_AND_STUDENTS_BY_TASK_ID =
            "select tr.student_id, " +
                    "tr.t_task_id, " +
                    "tr.task_answer, " +
                    "tr.teacher_comment, " +
                    "tr.mark, " +
                    "ua.id_account, " +
                    "ua.e_mail, " +
                    "ua.u_password, " +
                    "ua.first_name, " +
                    "ua.last_name, " +
                    "ua.account_role, " +
                    "ua.status_is_deleted " +
                    "from task_review as tr " +
                    "inner join user_account as ua on tr.student_id = ua.id_account " +
                    "where tr.t_task_id=?";

    @Override
    public boolean sendAnswer(int taskId, int userId, String answer) throws DaoException {
        boolean result = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_ANSWER)) {
            statement.setString(1, answer);
            statement.setInt(2, userId);
            statement.setInt(3, taskId);

            if(statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error(FAIL_SENDING_TASK_ANSWER, e);
            throw new DaoException(FAIL_SENDING_TASK_ANSWER, e);
        }
        return result;
    }

    @Override
    public boolean sendReview(int userId, int taskId, String comment, int mark) throws DaoException {
        boolean isSent = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(SEND_TASK_REVIEW)) {
            statement.setInt(1, mark);
            statement.setString(2, comment);
            statement.setInt(3, userId);
            statement.setInt(4, taskId);

            isSent = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(FAIL_SENDING_TASK_REVIEW, e);
            throw new DaoException(FAIL_SENDING_TASK_REVIEW, e);
        }
        return isSent;
    }

    @Override
    public List<TaskReviewDto> findAllReviewsByTaskId(int taskId) throws DaoException {
        List<TaskReviewDto>  taskReviewDtoList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_TASK_REVIEWS_AND_STUDENTS_BY_TASK_ID)) {
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskReviewDto taskReviewDto = ResultSetParser.createTaskReviewUsersDto(resultSet);
                taskReviewDtoList.add(taskReviewDto);
            }
        } catch (SQLException e) {
            LOG.error(FAIL_FINDING_TASK_REVIEWS_BY_TASK_ID, e);
            throw new DaoException(FAIL_FINDING_TASK_REVIEWS_BY_TASK_ID, e);
        }
        return taskReviewDtoList;
    }
}
