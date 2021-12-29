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
import epam.jwd.online_training.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class EditCourseCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(EditCourseCommand.class);
    private static final String UPDATE_COURSE_COMMAND_EXCEPTION = "Exception in updating course command. ";
    private static final String PUT_REQUIRED_LISTS_IN_SESSION_EXCEPTION = "Error putting required lists in the session";
    private static final String EDIT_COURSE_PAGE_PATH = PagePathManager.getProperty("path.page.editcourse");
    private static final String ALL_COURSES_PATH = "/controller?command=takeallcourses";
    private static final String UPDATE_SUCCESS_MESSAGE = "message.admin.course-update-success";
    private static final String UPDATE_FAIL_MESSAGE = "message.admin.course-update-fail";

    public EditCourseCommand() { super(ServiceManager.getCourseService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        ActionResult actionResult = null;
        Map<String, String[]> requestParameters = content.getRequestParameters();
        String[] editedCourseName = requestParameters.get(EntityAttribute.COURSE_TITLE);
        if (editedCourseName == null) {
            actionResult = putRequiredDataIntoSession(content);
        } else {
            boolean isUpdated = false;
            try {
                String idLine = content.getSingleRequestParameter(EntityAttribute.COURSE_ID);
                int courseId = Integer.parseInt(idLine);
                String title = content.getSingleRequestParameter(EntityAttribute.COURSE_TITLE);
                String description = content.getSingleRequestParameter(EntityAttribute.COURSE_DESCRIPTION);
                String typeLine = content.getSingleRequestParameter(EntityAttribute.COURSE_TYPE);
                int type = Integer.parseInt(typeLine);
                String teacherIdLine = content.getSingleRequestParameter(EntityAttribute.COURSE_TEACHER_ID);
                int teacherId = Integer.parseInt(teacherIdLine);
                String status = content.getSingleRequestParameter(EntityAttribute.COURSE_STATUS);
                String isAvailableLine = content.getSingleRequestParameter(EntityAttribute.COURSE_IS_AVAILABLE);
                int isAvailable = Integer.parseInt(isAvailableLine);

                CourseService courseService = (CourseService) getService();
                isUpdated = courseService.updateCourse(courseId, title, description, type, teacherId, status, isAvailable);
            } catch (ServiceException e) {
                LOG.error(UPDATE_COURSE_COMMAND_EXCEPTION, e);
                throw new CommandException(UPDATE_COURSE_COMMAND_EXCEPTION, e);
            }
            putMessageIntoSession(content, isUpdated, UPDATE_SUCCESS_MESSAGE, UPDATE_FAIL_MESSAGE);
            actionResult = new ActionResult(ALL_COURSES_PATH, NavigationType.REDIRECT);
        }
        return actionResult;

    }

    private ActionResult putRequiredDataIntoSession(RequestContent content) throws CommandException {
        ActionResult actionResult;
        try {
            CourseTypeService courseTypeService = ServiceManager.getCourseTypeService();
            List<CourseType> courseTypeList = courseTypeService.getAllLanguages();
            content.setSessionAttributes(EntityAttribute.ALL_COURSE_TYPE_PARAM, courseTypeList);

            UserService userReceiver = new UserServiceImpl();
            List<User> teacherList = userReceiver.getAllTeachers();
            content.setSessionAttributes(EntityAttribute.ALL_TEACHERS_PARAM, teacherList);

            actionResult = new ActionResult(EDIT_COURSE_PAGE_PATH, NavigationType.FORWARD);

        } catch (ServiceException e) {
            LOG.error(PUT_REQUIRED_LISTS_IN_SESSION_EXCEPTION, e);
            throw new CommandException(PUT_REQUIRED_LISTS_IN_SESSION_EXCEPTION, e);
        }
        return actionResult;
    }
}
