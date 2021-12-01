package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.TaskDto;
import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface TaskService extends Service {

    List<Task> getTaskByCourseId(int courseId) throws ServiceException;
    boolean addTask(int courseId, String title, String description) throws ServiceException;
    List<TaskDto> getReceivedTasks(int userId) throws ServiceException;
}
