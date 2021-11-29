package epam.jwd.online_training.logic;

import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface TaskService extends Service {

    List<Task> getTask(int courseId) throws ServiceException;
    boolean addTask(int courseId, String title, String description) throws ServiceException;
}
