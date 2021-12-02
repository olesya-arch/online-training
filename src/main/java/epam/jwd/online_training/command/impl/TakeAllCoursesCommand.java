package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.dto.CourseDto;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.CourseService;
import epam.jwd.online_training.service.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TakeAllCoursesCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(TakeAllCoursesCommand.class);
    private static final String SHOW_ALL_COURSES_COMMAND_EXCEPTION = "Exception occurred showing all courses command. ";
    private static final String ALL_COURSES_PATH = PagePathManager.getProperty("path.page.allcourses");
    private static final String ALL_COURSES_PARAM = "allCourses";

    public TakeAllCoursesCommand() { super(ServiceManager.getCourseService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        try {
            CourseService courseService = (CourseService) getService();
            List<CourseDto> courseDtoList = courseService.getAllCourses();
            content.setSessionAttributes(ALL_COURSES_PARAM, courseDtoList);
        } catch (ServiceException e) {
            LOGGER.error(SHOW_ALL_COURSES_COMMAND_EXCEPTION, e);
            throw new CommandException(SHOW_ALL_COURSES_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(ALL_COURSES_PATH, NavigationType.FORWARD);
    }
}
