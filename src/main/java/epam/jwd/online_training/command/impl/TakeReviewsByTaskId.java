package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.TaskReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TakeReviewsByTaskId extends Command {

    private static final Logger LOGGER = LogManager.getLogger(TakeReviewsByTaskId.class);
    private static final String SHOW_REVIEWS_BY_TASK_ID_COMMAND_EXCEPTION =
            "Exception in executing show reviews by task id command. ";
    private static final String TASK_REVIEWS_PAGE_PATH = PagePathManager.getProperty("path.page.taskreviews");
    private static final String TASK_ID_PARAM = "task_id";
    private static final String REVIEWS_AND_USERS_DTO_ATTRIBUTE = "reviewsAndUsers";

    public TakeReviewsByTaskId() { super(ServiceManager.getTaskReviewService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        String taskIdLine = content.getSingleRequestParameter(TASK_ID_PARAM);
        int taskId = Integer.parseInt(taskIdLine);

        try {
            TaskReviewService taskReviewReceiver = (TaskReviewService) getService();
            List<TaskReviewDto> reviewDtoList = taskReviewReceiver.getAllReviewsByTaskId(taskId);
            content.setSessionAttributes(REVIEWS_AND_USERS_DTO_ATTRIBUTE, reviewDtoList);
        } catch (ServiceException e) {
            LOGGER.error(SHOW_REVIEWS_BY_TASK_ID_COMMAND_EXCEPTION, e);
            throw new CommandException(SHOW_REVIEWS_BY_TASK_ID_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(TASK_REVIEWS_PAGE_PATH, NavigationType.FORWARD);
    }
}
