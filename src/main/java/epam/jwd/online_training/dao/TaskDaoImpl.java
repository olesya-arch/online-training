package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ProxyConnection;
import epam.jwd.online_training.dto.TaskDto;
import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl extends AbstractDao implements TaskDao{

    private static final Logger LOG = LogManager.getLogger(TaskDaoImpl.class);
    private static final String FAIL_ADDING_NEW_TASK = "Fail adding new task in DAO. ";
    private static final String FAIL_FINDING_TASKS_BY_COURSE_ID = "Fail finding tasks by course ID in DAO. ";
    private static final String FAIL_FINDING_RECEIVED_TASKS = "Fail finding received tasks in DAO. ";

    private static final String FIND_TASKS_BY_COURSE_ID =
            "select task_id, " +
                    "t_title, " +
                    "description, " +
                    "course_id " +
                    "from task where course_id=?";

    private static final String ADD_NEW_TASK =
            "insert into task (t_title, description, course_id) values (?,?,?)";

    private static final String FIND_RECEIVED_TASKS =
            "select t.task_id, " +
                    "t.t_title, " +
                    "t.description, " +
                    "t.course_id, " +
                    "tr.student_id, " +
                    "tr.t_task_id, " +
                    "tr.task_answer, " +
                    "tr.teacher_comment, " +
                    "tr.mark " +
                    "from task as t " +
                    "inner join task_review as tr " +
                    "on t.task_id = tr.t_task_id " +
                    "where tr.student_id=?";

    @Override
    public List<Task> findTaskByCourseId(int courseId) throws DaoException {
        List<Task> taskList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_TASKS_BY_COURSE_ID)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = ResultSetParser.createTask(resultSet);
                taskList.add(task);
            }
        } catch (SQLException e) {
            LOG.error(FAIL_FINDING_TASKS_BY_COURSE_ID, e);
            throw new DaoException(FAIL_FINDING_TASKS_BY_COURSE_ID, e);
        }
        return taskList;
    }

    @Override
    public boolean addTask(int courseId, String title, String description) throws DaoException {
        boolean isAdded = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(ADD_NEW_TASK)) {
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setInt(3, courseId);

            isAdded = statement.executeUpdate() != 0;

        } catch (SQLException e) {
            LOG.error(FAIL_ADDING_NEW_TASK, e);
            throw new DaoException(FAIL_ADDING_NEW_TASK, e);
        }
        return isAdded;
    }

    @Override
    public List<TaskDto> findReceivedTasks(int userId) throws DaoException {
        List<TaskDto> receivedTaskDtoList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_RECEIVED_TASKS)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TaskDto taskDto = ResultSetParser.createTaskDto(resultSet);
                receivedTaskDtoList.add(taskDto);
            }
        } catch (SQLException e) {
            LOG.error(FAIL_FINDING_RECEIVED_TASKS, e);
            throw new DaoException(FAIL_FINDING_RECEIVED_TASKS, e);
        }
        return receivedTaskDtoList;
    }
}
