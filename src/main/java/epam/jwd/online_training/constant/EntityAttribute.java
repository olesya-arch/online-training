package epam.jwd.online_training.constant;

public final class EntityAttribute {

    public static final String ALL_TEACHERS_PARAM = "allTeachers";

    public static final String USER_ID = "id_account";
    public static final String USER_EMAIL = "e_mail";
    public static final String USER_PASSWORD = "u_password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_ROLE = "account_role";

    public static final String COURSE_ID = "id_course";
    public static final String COURSE_TYPE = "category";
    public static final String COURSE_TEACHER_ID = "teacher_id";
    public static final String COURSE_STATUS = "course_status";
    public static final String COURSE_IS_AVAILABLE = "is_available";
    public static final String COURSE_TITLE = "c_title";
    public static final String COURSE_DESCRIPTION = "c_description";

    public static final String TASK_ID = "id_task";
    public static final String TASK_TITLE = "t_title";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_COURSE_ID = "course_id";

    public static final String TASK_REVIEW_STUDENT_ID = "student_id";
    public static final String TASK_REVIEW_TASK_ID = "task_id";
    public static final String TASK_REVIEW_COMMENT = "teacher_comment";
    public static final String TASK_REVIEW_MARK = "mark";

    public EntityAttribute(){
    }

}
