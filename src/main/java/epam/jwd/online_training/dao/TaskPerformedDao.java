package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.TaskPerformed;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface TaskPerformedDao {

    boolean sendTask(int taskId, int userId) throws DaoException;
    boolean sendReview(int taskId, int userId, String comment, int mark) throws  DaoException;
    List<TaskPerformed> findAllReviewsByTaskId(int taskId) throws DaoException;
}
