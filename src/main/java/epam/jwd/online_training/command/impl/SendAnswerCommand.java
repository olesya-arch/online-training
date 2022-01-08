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
import epam.jwd.online_training.service.TaskReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SendAnswerCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(SendAnswerCommand.class);
    private static final String SEND_ANSWER_COMMAND_EXCEPTION = "Exception in processing send answer command.";
    private static final String RECEIVED_TASKS_PAGE = "/controller?command=takereceivedtasks";
    private static final String SEND_SUCCESS_MESSAGE = "message.student.answer-send-success";
    private static final String SEND_FAIL_MESSAGE = "message.student.answer-send-fail";
    private static final String TASK_ID_PARAM = "taskid";
    private static final String ANSWER_PARAM = "answer";

    public SendAnswerCommand() { super(ServiceManager.getTaskReviewService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        boolean isSent;
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        String taskIdLine = content.getSingleRequestParameter(TASK_ID_PARAM);
        int taskId = Integer.parseInt(taskIdLine);
        User user = (User) sessionAttributes.get(SessionAttribute.USER);
        int userId = user.getId();
        String answer = content.getSingleRequestParameter(ANSWER_PARAM);
        try {
            TaskReviewService taskReviewService = (TaskReviewService) getService();
            isSent = taskReviewService.sendAnswer(taskId, userId, answer);
        } catch (ServiceException e) {
            LOGGER.error(SEND_ANSWER_COMMAND_EXCEPTION, e);
            throw new CommandException(SEND_ANSWER_COMMAND_EXCEPTION, e);
        }
        putMessageIntoSession(content, isSent, SEND_SUCCESS_MESSAGE, SEND_FAIL_MESSAGE);
        return new ActionResult(RECEIVED_TASKS_PAGE, NavigationType.REDIRECT);
    }
}
