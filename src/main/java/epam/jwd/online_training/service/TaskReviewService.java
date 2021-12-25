package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface TaskReviewService extends Service {

    boolean sendAnswer(int userId, int taskId, String answer) throws ServiceException;
    boolean sendReview(int userId, int taskId, String comment, int mark) throws ServiceException;
    List<TaskReviewDto> getAllReviewsByTaskId(int taskId) throws ServiceException;
}
