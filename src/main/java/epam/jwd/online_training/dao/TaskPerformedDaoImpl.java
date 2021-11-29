package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.TaskPerformed;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TaskPerformedDaoImpl extends AbstractDao implements TaskPerformedDao {

    private static final Logger LOG = LogManager.getLogger(TaskPerformedDaoImpl.class);

    private static final String SEND_REVIEW =
            "update task_performed set mark=?, " +
                    "teacher_comment=? " +
                    "where student_id=? " +
                    "and task_id=?";

    @Override
    public boolean sendReview(int userId, int taskId, String comment, int mark) throws DaoException {
        return false;
    }

    @Override
    public List<TaskPerformed> findAllReviewsByTaskId(int taskId) throws DaoException {
        return null;
    }
}
