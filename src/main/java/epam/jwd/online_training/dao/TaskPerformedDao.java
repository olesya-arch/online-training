package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.TaskPerformed;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface TaskPerformedDao {

    boolean sendReview(int userId, int taskId, String comment, int mark) throws  DaoException;
    List<TaskPerformed> findAllReviewsByTaskId(int taskId) throws DaoException;
}
