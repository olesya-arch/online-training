package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class JoinCourseCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(JoinCourseCommand.class);
    private static final String FAIL_EXECUTING_JOIN_COURSE_COMMAND = "Failed executing join course command. ";
    private static final String COURSE_ID_PARAMETER = "id_course";
    private static final String TAKEN_COURSES_PATH = "/controller?command=gettakencourses";
    private static final String JOIN_SUCCESS_MESSAGE = "message.student.course-join-success";
    private static final String JOIN_FAIL_MESSAGE = "message.student.course-join-fail";

    public JoinCourseCommand() { super(ServiceManager.getUserService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttribute.USER);
        int userId = user.getId();

        String courseIdLine = content.getSingleRequestParameter(COURSE_ID_PARAMETER);
        int courseId = Integer.parseInt(courseIdLine);

        UserService userReceiver = (UserService) getService();
        boolean isJoined = false;
        try {
            isJoined = userReceiver.joinCourse(courseId, userId);
        } catch (ServiceException e) {
            LOG.error(FAIL_EXECUTING_JOIN_COURSE_COMMAND, e);
            throw new CommandException(FAIL_EXECUTING_JOIN_COURSE_COMMAND, e);
        }
        putMessageIntoSession(content, isJoined, JOIN_SUCCESS_MESSAGE, JOIN_FAIL_MESSAGE);

        return new ActionResult(TAKEN_COURSES_PATH, NavigationType.REDIRECT);
    }
}
