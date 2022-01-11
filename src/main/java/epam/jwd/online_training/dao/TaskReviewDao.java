package epam.jwd.online_training.dao;

import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface TaskReviewDao {

    boolean sendAnswer(int taskId, int userId, String answer) throws DaoException;
    boolean sendReview(int userId, int taskId, String comment, int mark) throws  DaoException;
    List<TaskReviewDto> findAllReviewsByTaskId(int taskId) throws DaoException;
}
