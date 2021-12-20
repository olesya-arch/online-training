package epam.jwd.online_training.command;

import epam.jwd.online_training.command.impl.*;

import java.util.EnumMap;
import java.util.Map;

import static epam.jwd.online_training.command.CommandType.*;

public class CommandMap {

    private static Map<CommandType, Command> commandMap = new EnumMap<>(CommandType.class);

    static {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(SIGNUP, new StudentSignUpCommand());
        commandMap.put(LOCALE, new LocaleCommand());
        commandMap.put(GETPAGE, new GetPageCommand());
        commandMap.put(RECOVERPASSWORD, new RecoverPasswordCommand());

        commandMap.put(ADDCOURSE, new AddCourseCommand());
        commandMap.put(ADDTEACHER, new TeacherSignUpCommand());
        commandMap.put(TAKEALLTEACHERS, new TakeAllTeachersCommand());
        commandMap.put(TAKEALLCOURSES, new TakeAllCoursesCommand());
        commandMap.put(EDITCOURSE, new EditCourseCommand());
        commandMap.put(DELETEUSER, new DeleteUserCommand());

        commandMap.put(TAKETEACHERRELATEDCOURSES, new TakeTeacherRelatedCoursesCommand());
        commandMap.put(TAKECOURSERELATEDTASKS, new TakeCourseRelatedTasksCommand());
        commandMap.put(TAKEREVIEWBYTASKID, new TakeReviewsByTaskId());
        commandMap.put(SENDREVIEW, new SendReviewCommand());
        commandMap.put(ADDTASK, new AddTaskCommand());

        commandMap.put(TAKEAVAILABLECOURSES, new TakeAvailableCoursesCommand());
        commandMap.put(TAKERECEIVEDTASKS, new TakeReceivedTasksCommand());
        commandMap.put(GETTAKENCOURSES, new GetTakenCoursesCommand());
        commandMap.put(JOINCOURSE, new JoinCourseCommand());
    }

    public CommandMap() {
    }

    public static Command defineCommandType(String command) {
        CommandType commandType = CommandType.valueOf(command);
        return commandMap.get(commandType);
    }
}
