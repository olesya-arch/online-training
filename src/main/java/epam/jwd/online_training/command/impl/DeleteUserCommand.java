package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.logic.ServiceManager;
import epam.jwd.online_training.logic.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class DeleteUserCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(DeleteUserCommand.class);
    private static final String FAIL_EXECUTING_DELETE_USER_COMMAND = "Failed executing delete user command. ";
    private static final String USER_ID_PARAMETER = "userid";
    private static final String DELETE_SUCCESS_MESSAGE_KEY = "message.admin.teacher-delete-success";
    private static final String DELETE_FAIL_MESSAGE_KEY = "message.admin.teacher-delete-fail";
    private static final String GET_PAGE_URL_PARAMETER = "/controller?command=takeallteachers";

    public DeleteUserCommand() { super(ServiceManager.getUserService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        String userIdLine = content.getSingleRequestParameter(USER_ID_PARAMETER);
        int userId = Integer.parseInt(userIdLine);
        Map<String, Object> sessionAttributes = content.getSessionAttributes();
        String locale = (String) sessionAttributes.get(SessionAttribute.LOCALE);
        UserService userReceiver = (UserService) getService();
        boolean isDeleteSuccessfully = false;
        try {
            isDeleteSuccessfully = userReceiver.deleteUser(userId);
        } catch (ServiceException e) {
            LOG.error(FAIL_EXECUTING_DELETE_USER_COMMAND, e);
            throw new CommandException(FAIL_EXECUTING_DELETE_USER_COMMAND, e);
        }
        putMessageIntoSession(content, isDeleteSuccessfully, DELETE_SUCCESS_MESSAGE_KEY, DELETE_FAIL_MESSAGE_KEY);

        return new ActionResult(GET_PAGE_URL_PARAMETER, NavigationType.REDIRECT);
    }
}
