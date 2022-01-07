package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.constant.EntityAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.CourseService;
import epam.jwd.online_training.service.CourseTypeService;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddCourseCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(AddCourseCommand.class);
    private static final String ADD_COURSE_COMMAND_EXCEPTION = "Exception in adding course command. ";
    private static final String PUT_REQUIRED_LISTS_IN_SESSION_EXCEPTION = "Error putting required lists in the session";
    private static final String ADD_COURSE_PAGE_PATH = PagePathManager.getProperty("path.page.addcourse");
    private static final String ADD_SUCCESS_MESSAGE_KEY = "message.admin.course-add-success";
    private static final String ADD_FAIL_MESSAGE_KEY = "message.admin.course-add-fail";
    private static final String ADD_COURSE_PARAM = "addCourse";
    private static final String GET_PAGE_URL_PARAM = "/controller?command=addcourse";

    public AddCourseCommand() {
        super(ServiceManager.getCourseService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        ActionResult actionResult = null;
        Map<String, String[]> requestParameters = content.getRequestParameters();
        String[] addCourseParam = requestParameters.get(ADD_COURSE_PARAM);
        if (addCourseParam == null) {
            actionResult = putRequiredDataIntoSession(content);
        } else {
            boolean isAdded = processCourseAdding(content);

            putMessageIntoSession(content, isAdded, ADD_SUCCESS_MESSAGE_KEY, ADD_FAIL_MESSAGE_KEY);

            actionResult = new ActionResult(GET_PAGE_URL_PARAM, NavigationType.REDIRECT);
        }
        return actionResult;
    }

    private boolean processCourseAdding(RequestContent content) throws CommandException {
        boolean isAdded;
        try {
            String title = content.getSingleRequestParameter(EntityAttribute.COURSE_TITLE);
            String description = content.getSingleRequestParameter(EntityAttribute.COURSE_DESCRIPTION);
            String typeLine = content.getSingleRequestParameter(EntityAttribute.COURSE_TYPE);
            int typeId = Integer.parseInt(typeLine);
            String teacherIdLine = content.getSingleRequestParameter(EntityAttribute.COURSE_TEACHER_ID);
            int teacherId = Integer.parseInt(teacherIdLine);
            String status = content.getSingleRequestParameter(EntityAttribute.COURSE_STATUS);
            String isAvailableLine = content.getSingleRequestParameter(EntityAttribute.COURSE_IS_AVAILABLE);
            int isAvailable = Integer.parseInt(isAvailableLine);

            CourseService courseService = (CourseService) getService();
            isAdded = courseService.addCourse(title, description, typeId, teacherId, status, isAvailable);
        } catch (ServiceException e) {
            LOG.error(ADD_COURSE_COMMAND_EXCEPTION, e);
            throw new CommandException(ADD_COURSE_COMMAND_EXCEPTION, e);
        }
        return isAdded;
    }

    private ActionResult putRequiredDataIntoSession(RequestContent content) throws CommandException {
        ActionResult actionResult;
        try {
            CourseTypeService courseTypeService = ServiceManager.getCourseTypeService();
            List<CourseType> courseTypeList = courseTypeService.getAllLanguages();
            content.setSessionAttributes(EntityAttribute.ALL_COURSE_TYPE_PARAM, courseTypeList);

            UserService userService = ServiceManager.getUserService();
            List<User> teachersList = userService.getAllTeachers();
            content.setSessionAttributes(EntityAttribute.ALL_TEACHERS_PARAM, teachersList);

            actionResult = new ActionResult(ADD_COURSE_PAGE_PATH, NavigationType.FORWARD);

        } catch (ServiceException e) {
            LOG.error(PUT_REQUIRED_LISTS_IN_SESSION_EXCEPTION, e);
            throw new CommandException(PUT_REQUIRED_LISTS_IN_SESSION_EXCEPTION, e);
        }
        return actionResult;
    }
}
