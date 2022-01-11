package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddTaskCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(AddTaskCommand.class);
    private static final String COURSE_ID = "id_course";
    private static final String TASK_TITLE = "t_title";
    private static final String TASK_DESCRIPTION = "description";
    private static final String ADD_SUCCESS_MESSAGE = "message.teacher.task-add-success";
    private static final String ADD_FAIL_MESSAGE = "message.teacher.task-add-fail";
    private static final String ADD_TASK_PAGE = "/controller?command=taketeacherrelatedcourses&expectedpage=addtask";
    private static final String ADD_TASK_COMMAND_EXCEPTION = "Exception in process of adding task command. ";

    public AddTaskCommand() { super(ServiceManager.getTaskService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        boolean isAdded = false;

        String courseIdLine = content.getSingleRequestParameter(COURSE_ID);
        int courseId = Integer.parseInt(courseIdLine);
        String taskTitle = content.getSingleRequestParameter(TASK_TITLE);
        String taskDescription = content.getSingleRequestParameter(TASK_DESCRIPTION);
        try {
            TaskService taskService = (TaskService) getService();
            isAdded = taskService.addTask(courseId, taskTitle, taskDescription);
        } catch (ServiceException e) {
            LOG.error(ADD_TASK_COMMAND_EXCEPTION, e);
            throw new CommandException(ADD_TASK_COMMAND_EXCEPTION,e);
        }
        putMessageIntoSession(content, isAdded, ADD_SUCCESS_MESSAGE, ADD_FAIL_MESSAGE);
        return new ActionResult(ADD_TASK_PAGE, NavigationType.REDIRECT);
    }
}
