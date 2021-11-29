package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TaskDaoImpl extends AbstractDao implements TaskDao{

    private static final Logger LOG = LogManager.getLogger(TaskDaoImpl.class);

    private static final String FIND_TASKS_BY_COURSE_ID =
            "select id_task, t_title, description, course_id " +
                    "from task where course_id=?";

    private static final String ADD_NEW_TASK =
            "insert into task (t_title, description, course_id) values (?,?,?)";

    private static final String FIND_RECEIVED_TASKS =
            "select id_task, t_title, description, course_id, " +
                    "task_id, teacher_comment, mark " +
                    "from task " +
                    "inner join task_performed tp " +
                    "on task.id_task = tp.task_id " +
                    "where tp.student_id=?";

    @Override
    public List<Task> findTaskByCourseId(int courseId) throws DaoException {
        return null;
    }

    @Override
    public boolean addTask(int courseId, String title, String description) throws DaoException {
        return false;
    }

    @Override
    public List<Task> findReceivedTasks(int userId) throws DaoException {
        return null;
    }
}
