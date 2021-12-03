package epam.jwd.online_training.constant;

public final class EntityAttribute {

    public static final String ALL_TEACHERS_PARAM = "allTeachers";

    public static final String USER_ID = "u_a.id_account";
    public static final String USER_EMAIL = "u_a.e_mail";
    public static final String USER_PASSWORD = "u_a.u_password";
    public static final String USER_FIRST_NAME = "u_a.first_name";
    public static final String USER_LAST_NAME = "u_a.last_name";
    public static final String USER_ROLE = "r.account_role";

    public static final String COURSE_ID = "c.id_course";
    public static final String COURSE_TYPE = "ct.category";
    public static final String COURSE_TEACHER_ID = "c.teacher_id";
    public static final String COURSE_STATUS = "cs.course_status";
    public static final String COURSE_IS_AVAILABLE = "c.is_available";
    public static final String COURSE_TITLE = "c.c_title";
    public static final String COURSE_DESCRIPTION = "c.c_description";

    public static final String TASK_ID = "id_task";
    public static final String TASK_TITLE = "t_title";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_COURSE_ID = "course_id";

    public static final String TASK_REVIEW_STUDENT_ID = "tr.student_id";
    public static final String TASK_REVIEW_TASK_ID = "tr.task_id";
    public static final String TASK_REVIEW_COMMENT = "tr.teacher_comment";
    public static final String TASK_REVIEW_MARK = "tr.mark";

    public EntityAttribute(){
    }

}
