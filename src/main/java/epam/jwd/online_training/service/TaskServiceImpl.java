package epam.jwd.online_training.service;

import epam.jwd.online_training.dao.DAOManager;
import epam.jwd.online_training.dao.TaskDaoImpl;
import epam.jwd.online_training.dao.TransactionManager;
import epam.jwd.online_training.dto.TaskDto;
import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.DaoException;
import epam.jwd.online_training.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TaskServiceImpl implements TaskService {

    private static final Logger LOG = LogManager.getLogger(TaskServiceImpl.class);
    private static TaskDaoImpl taskDao = DAOManager.getTaskDao();

    private static final String RECEIVE_TASK_BY_COURSE_ID_EXCEPTION =
            "Exception in process of receiving tasks by course ID in service. ";
    private static final String ADD_NEW_TASK_EXCEPTION = "Exception in process of adding a new task in service. ";
    private static final String SHOW_RECEIVED_TASKS_EXCEPTION =
            "Exception in process of showing received tasks in service. ";

    @Override
    public List<Task> getTaskByCourseId(int courseId) throws ServiceException {
        List<Task> taskList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(taskDao)) {
            taskList = taskDao.findTaskByCourseId(courseId);
        } catch (SQLException | DaoException e) {
            LOG.error(RECEIVE_TASK_BY_COURSE_ID_EXCEPTION, e);
            throw new ServiceException(RECEIVE_TASK_BY_COURSE_ID_EXCEPTION, e);
        }
        return taskList;
    }

    @Override
    public boolean addTask(int courseId, String title, String description) throws ServiceException {
        boolean isAdded = false;
        try(TransactionManager tm = TransactionManager.launchTransaction(taskDao)) {
            isAdded = taskDao.addTask(courseId, title, description);
        } catch (SQLException | DaoException e) {
            LOG.error(ADD_NEW_TASK_EXCEPTION, e);
            throw new ServiceException(ADD_NEW_TASK_EXCEPTION, e);
        }
        return isAdded;
    }

    @Override
    public List<TaskDto> getReceivedTasks(int userId) throws ServiceException {
        List<TaskDto> taskDtoList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(taskDao)) {
            taskDtoList = taskDao.findReceivedTasks(userId);
        } catch (SQLException | DaoException e) {
            LOG.error(SHOW_RECEIVED_TASKS_EXCEPTION, e);
            throw new ServiceException(SHOW_RECEIVED_TASKS_EXCEPTION, e);
        }
        return taskDtoList;
    }
}
