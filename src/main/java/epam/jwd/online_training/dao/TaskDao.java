package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface TaskDao {

    List<Task> findTaskByCourseId(int courseId) throws DaoException;
    boolean addTask(int courseId, String title, String description) throws DaoException;
}
