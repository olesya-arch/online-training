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

public class GetTakenCoursesCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(GetTakenCoursesCommand.class);
    private static final String SHOW_TAKEN_COURSES_COMMAND_EXCEPTION = "Exception occurred showing taken courses command. ";
    private static final String TAKEN_COURSES_PATH = PagePathManager.getProperty("path.page.takencourses");
    private static final String TAKEN_COURSES_PARAM = "takenCourses";

    public GetTakenCoursesCommand() { super(ServiceManager.getCourseService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttribute.USER);
        int userId = user.getId();

        try {
            CourseService courseService = (CourseService) getService();
            List<CourseDto> courseDtoList = courseService.getTakenCourses(userId);
            content.setRequestAttributes(TAKEN_COURSES_PARAM, courseDtoList);
        } catch (ServiceException e) {
            LOGGER.error(SHOW_TAKEN_COURSES_COMMAND_EXCEPTION);
            throw new CommandException(SHOW_TAKEN_COURSES_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(TAKEN_COURSES_PATH, NavigationType.FORWARD);
    }
}
