package epam.jwd.online_training.service;

public class ServiceManager {

    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final CourseService COURSE_SERVICE = new CourseServiceImpl();
    private static final TaskServiceImpl TASK_SERVICE = new TaskServiceImpl();
    private static final TaskReviewService TASK_REVIEW_SERVICE = new TaskReviewServiceImpl();

    public ServiceManager() {
    }

    public static UserService getUserService() { return USER_SERVICE; }

    public static CourseService getCourseService() { return COURSE_SERVICE; }

    public static TaskServiceImpl getTaskService() { return TASK_SERVICE; }

    public static TaskReviewService getTaskReviewService() { return TASK_REVIEW_SERVICE; }
}
