package epam.jwd.online_training.dao;

public class DAOManager {

    private static final UserDaoImpl USER_DAO = new UserDaoImpl();
    private static final CourseDaoImpl COURSE_DAO = new CourseDaoImpl();
    private static final TaskDaoImpl TASK_DAO = new TaskDaoImpl();
    private static final TaskReviewDaoImpl TASK_REVIEW_DAO = new TaskReviewDaoImpl();
    private static final CourseTypeDaoImpl COURSE_TYPE_DAO = new CourseTypeDaoImpl();

    private DAOManager() {
    }

    public static UserDaoImpl getUserDao() { return USER_DAO; }

    public static CourseDaoImpl getCourseDao() { return COURSE_DAO; }

    public static TaskDaoImpl getTaskDao() { return TASK_DAO; }

    public static TaskReviewDaoImpl getTaskReviewDao() { return TASK_REVIEW_DAO; }

    public static CourseTypeDaoImpl getCourseTypeDao() { return COURSE_TYPE_DAO; }
}
