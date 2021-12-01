package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TakeCourseRelatedTasksCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(TakeCourseRelatedTasksCommand.class);
    private static final String TASKS_PAGE_PATH = PagePathManager.getProperty("path.page.relatedtasks");
    private static final String COURSE_ID_PARAM = "course_id";
    private static final String RELATED_TASKS_ATTR = "relatedTasks";
    private static final String SHOW_COURSE_RELATED_TASK_COMMAND_EXCEPTION =
            "Exception occurred showing course related tasks command. ";

    public TakeCourseRelatedTasksCommand() { super(ServiceManager.getTaskService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        String courseIdLine = content.getSingleRequestParameter(COURSE_ID_PARAM);
        Integer courseId = Integer.parseInt(courseIdLine);
        TaskService taskService = (TaskService) getService();
        try {
            List<Task> taskList = taskService.getTaskByCourseId(courseId);
            content.setSessionAttributes(RELATED_TASKS_ATTR, taskList);
        } catch (ServiceException e) {
            LOG.error(SHOW_COURSE_RELATED_TASK_COMMAND_EXCEPTION, e);
            throw new CommandException(SHOW_COURSE_RELATED_TASK_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(TASKS_PAGE_PATH, NavigationType.FORWARD);
    }
}
