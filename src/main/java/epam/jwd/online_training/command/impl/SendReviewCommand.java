package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.TaskReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendReviewCommand extends Command {

    private static final Logger LOGGER = LogManager.getLogger(SendReviewCommand.class);
    private static final String SEND_TASK_REVIEW_COMMAND_EXCEPTION = "Exception in process of executing send review command. ";
    private static final String SHOW_REVIEWS_BY_TASK_ID_PAGE = "/controller?command=takereviewsbytaskid&taskid=";
    private static final String SEND_SUCCESS_MESSAGE = "message.teacher.review-sent-success";
    private static final String SEND_FAIL_MESSAGE = "message.teacher.review-sent-fail";

    private static final String TASK_ID_PARAM = "task_id";
    private static final String USER_ID_PARAM = "id_account";
    private static final String TASK_MARK_PARAM = "mark";
    private static final String TASK_REVIEW_PARAM = "teacher_comment";

    public SendReviewCommand() { super(ServiceManager.getTaskReviewService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        boolean isSent = false;

        String taskIdLine = content.getSingleRequestParameter(TASK_ID_PARAM);
        int taskId = Integer.parseInt(taskIdLine);
        String studentIdLine = content.getSingleRequestParameter(USER_ID_PARAM);
        int studentId = Integer.parseInt(studentIdLine);
        String markLine = content.getSingleRequestParameter(TASK_MARK_PARAM);
        int mark = Integer.parseInt(markLine);
        String teacherComment = content.getSingleRequestParameter(TASK_REVIEW_PARAM);

        try {
            TaskReviewService taskReviewService = (TaskReviewService) getService();
            isSent = taskReviewService.sendReview(studentId, taskId, teacherComment, mark);
        } catch (ServiceException e) {
            LOGGER.error(SEND_TASK_REVIEW_COMMAND_EXCEPTION, e);
            throw new CommandException(SEND_TASK_REVIEW_COMMAND_EXCEPTION, e);
        }
        String showReviewPage = SHOW_REVIEWS_BY_TASK_ID_PAGE + taskIdLine;
        putMessageIntoSession(content, isSent, SEND_SUCCESS_MESSAGE, SEND_FAIL_MESSAGE);

        return new ActionResult(showReviewPage, NavigationType.REDIRECT);
    }
}
