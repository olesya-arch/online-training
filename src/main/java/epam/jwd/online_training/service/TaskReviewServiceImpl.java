package epam.jwd.online_training.service;

import epam.jwd.online_training.dao.DAOManager;
import epam.jwd.online_training.dao.TaskReviewDaoImpl;
import epam.jwd.online_training.dao.TransactionManager;
import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.exception.DaoException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.util.ScriptSecurityChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TaskReviewServiceImpl implements TaskReviewService {

    private static final Logger LOG = LogManager.getLogger(TaskReviewServiceImpl.class);
    private static TaskReviewDaoImpl taskReviewDao = DAOManager.getTaskReviewDao();

    private static final String SEND_TASK_ANSWER_EXCEPTION = "Exception in process of sending answer in service. ";
    private static final String SEND_TASK_REVIEW_EXCEPTION = "Exception in process of sending review in service. ";
    private static final String RECEIVE_ALL_TASK_REVIEWS_EXCEPTION = "Exception in process of receiving all reviews by task id. ";

    @Override
    public boolean sendAnswer(int taskId, int userId, String answer) throws ServiceException {
        boolean result = false;
        try(TransactionManager tm = TransactionManager.launchTransaction(taskReviewDao)) {
            answer = ScriptSecurityChecker.checkScript(answer);
            result = taskReviewDao.sendAnswer(taskId, userId, answer);
        } catch (SQLException | DaoException e) {
            LOG.error(SEND_TASK_ANSWER_EXCEPTION, e);
            throw new ServiceException(SEND_TASK_ANSWER_EXCEPTION, e);
        }
        return result;
    }

    @Override
    public boolean sendReview(int userId, int taskId, String comment, int mark) throws ServiceException {
        boolean isSent = false;
        try(TransactionManager tm = TransactionManager.launchTransaction(taskReviewDao)) {
            comment = ScriptSecurityChecker.checkScript(comment);
            isSent = taskReviewDao.sendReview(userId, taskId, comment, mark);
        } catch (SQLException | DaoException e) {
            LOG.error(SEND_TASK_REVIEW_EXCEPTION, e);
            throw new ServiceException(SEND_TASK_REVIEW_EXCEPTION, e);
        }
        return isSent;
    }

    @Override
    public List<TaskReviewDto> getAllReviewsByTaskId(int taskId) throws ServiceException {
        List<TaskReviewDto> taskReviewDtoList = null;
        try(TransactionManager tm = TransactionManager.launchQuery(taskReviewDao)) {
            taskReviewDtoList = taskReviewDao.findAllReviewsByTaskId(taskId);
        } catch (SQLException | DaoException e) {
            LOG.error(RECEIVE_ALL_TASK_REVIEWS_EXCEPTION, e);
            throw new ServiceException(RECEIVE_ALL_TASK_REVIEWS_EXCEPTION, e);
        }
        return taskReviewDtoList;
    }
}
