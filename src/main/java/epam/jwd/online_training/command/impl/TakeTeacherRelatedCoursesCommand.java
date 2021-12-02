package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.CourseService;
import epam.jwd.online_training.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class TakeTeacherRelatedCoursesCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(TakeTeacherRelatedCoursesCommand.class);
    private static final String SHOW_TEACHER_RELATED_COURSES_COMMAND =
            "Exception occurred showing all courses related to the teacher command. ";
    private static final String RELATED_COURSES_ATTRIBUTE = "relatedCourses";
    private static final String RELATED_COURSES = "relatedcourses";
    private static final String RELATED_TASKS = "relatedtasks";
    private static final String ADD_TASK = "addtask";
    private static final String EXPECTED_PAGE_PARAMETER = "expectedpage";
    private static final String RELATED_COURSES_PAGE = PagePathManager.getProperty("path.page.relatedcourses");
    private static final String RELATED_TASKS_PAGE = PagePathManager.getProperty("path.page.relatedtasks");
    private static final String ADD_TASK_PAGE = PagePathManager.getProperty("path.page.addtask");

    public TakeTeacherRelatedCoursesCommand() {
        super(ServiceManager.getCourseService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttribute.USER);
        int teacherId = user.getId();
        try {
            CourseService courseService = (CourseService) getService();
            List<CourseDto> courseDtoList = courseService.getRelatedCourses(teacherId);
            content.setSessionAttributes(RELATED_COURSES_ATTRIBUTE, courseDtoList);
        } catch (ServiceException e) {
            LOGGER.error(SHOW_TEACHER_RELATED_COURSES_COMMAND, e);
            throw new CommandException(SHOW_TEACHER_RELATED_COURSES_COMMAND, e);
        }
        String page = parseExpectedPage(content);

        return new ActionResult(page, NavigationType.FORWARD);
    }

    private String parseExpectedPage(RequestContent content) {
        String expectedPath = content.getSingleRequestParameter(EXPECTED_PAGE_PARAMETER);
        String page = null;
        if (expectedPath != null) {
            switch (expectedPath) {
                case RELATED_COURSES:
                    page = RELATED_COURSES_PAGE;
                    break;
                case RELATED_TASKS:
                    page = RELATED_TASKS_PAGE;
                    break;
                case ADD_TASK:
                    page = ADD_TASK_PAGE;
                    break;
            }
        }
        return page;
    }
}
