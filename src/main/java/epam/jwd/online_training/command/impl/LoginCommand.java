package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.constant.SighUpAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.entity.UserRole;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class LoginCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    private static final String LOGIN_COMMAND_EXCEPTION = "Exception occurred during login command! ";

    private static final String AUTHORIZATION_ATTRIBUTE = "authorization";
    private static final String FAIL_ATTRIBUTE = "actionFail";
    private static final String MESSAGE_LOGIN_ERROR = "message.login.error";
    private static final String LOGIN_PAGE = "/controller?command=getPage&expectedPage=login";
    private static final String ADMIN_PAGE = "/controller?command=getPage&expectedPage=adminpage";
    private static final String TEACHER_PAGE = "/controller?command=getPage&expectedPage=teacherpage";
    private static final String STUDENT_PAGE = "/controller?command=getPage&expectedPage=studentpage";

    public LoginCommand() { super(ServiceManager.getUserService());}

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        User user = null;
        try {
            UserService userService = (UserService) getService();
            String emailInput = content.getSingleRequestParameter(SighUpAttribute.EMAIL_PARAMETER);
            String passwordInput = content.getSingleRequestParameter(SighUpAttribute.PASSWORD_PARAMETER);
            user = userService.login(emailInput,passwordInput);
        } catch (ServiceException e) {
            LOG.error(LOGIN_COMMAND_EXCEPTION, e);
            throw new CommandException(LOGIN_COMMAND_EXCEPTION, e);
        }
        ActionResult actionResult = null;
        Map<String, Object> requestAttributes = content.getRequestAttributes();
        if (user != null) {
            requestAttributes.put(AUTHORIZATION_ATTRIBUTE, Boolean.TRUE);
            content.setSessionAttributes(SessionAttribute.USER, user);
            UserRole role = user.getRole();
            if (role == UserRole.ADMIN) {
                actionResult = new ActionResult(ADMIN_PAGE, NavigationType.REDIRECT);
            } else if (role == UserRole.TEACHER) {
                actionResult = new ActionResult(TEACHER_PAGE, NavigationType.REDIRECT);
            } else {
                actionResult = new ActionResult(STUDENT_PAGE, NavigationType.REDIRECT);
            }
        } else {
            requestAttributes.put(AUTHORIZATION_ATTRIBUTE, Boolean.FALSE);
            content.setSessionAttributes(FAIL_ATTRIBUTE, MESSAGE_LOGIN_ERROR);
            actionResult = new ActionResult(LOGIN_PAGE, NavigationType.REDIRECT);
        }
        return actionResult;
    }
}
