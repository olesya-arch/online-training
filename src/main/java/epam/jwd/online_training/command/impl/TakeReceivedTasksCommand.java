package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.dto.TaskDto;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class TakeReceivedTasksCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(TakeReceivedTasksCommand.class);
    private static final String RECEIVED_TASKS_PATH = PagePathManager.getProperty("path.page.received-tasks");
    private static final String RECEIVED_TASKS_PARAM = "receivedTasks";
    private static final String SHOW_RECEIVED_TASKS_COMMAND_EXCEPTION = "Exception in showing received tasks command";

    public TakeReceivedTasksCommand() { super(ServiceManager.getTaskService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        User user = (User) sessionAttributes.get(SessionAttribute.USER);
        int userId = user.getId();
        try {
            TaskService tasksReceiver = (TaskService) getService();
            List<TaskDto> taskDtoList = tasksReceiver.getReceivedTasks(userId);
            content.setRequestAttributes(RECEIVED_TASKS_PARAM, taskDtoList);
        } catch (ServiceException e) {
            LOG.error(SHOW_RECEIVED_TASKS_COMMAND_EXCEPTION, e);
            throw new CommandException(SHOW_RECEIVED_TASKS_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(RECEIVED_TASKS_PATH, NavigationType.FORWARD);
    }
}
