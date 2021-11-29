package epam.jwd.online_training.logic;

import epam.jwd.online_training.entity.TaskPerformed;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface TaskPerformedService extends Service {

    boolean sendTask(int taskId, int userId) throws ServiceException;
    boolean sendReview(int taskId, int userId, String comment, int mark) throws ServiceException;
    List<TaskPerformed> getAllReviewsByTaskId(int taskId) throws ServiceException;
}
