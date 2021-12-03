package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class RecoverPasswordCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(RecoverPasswordCommand.class);
    private static final String ERROR_RECOVER_PASSWORD = "Fail to recover password for {} ";
    private static final String EMAIL_PARAMETER = "u_a.e_mail";
    private static final String MESSAGE_MAIL_SUBJECT = "mail.message.subject";
    private static final String MESSAGE_MAIL_TEXT = "mail.message.text";
    private static final String PASSWORD_CHANGE_SUCCESS_MESSAGE = "message.password.change-success";
    private static final String PASSWORD_CHANGE_FAIL_MESSAGE = "message.password.change-fail";
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";
    private static final String RECOVERY_PAGE_PATH = "/controller?command=getPage&expectedPage=recovery";

    public RecoverPasswordCommand() { super(ServiceManager.getUserService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        boolean isSent = false;
        String email = content.getSingleRequestParameter(EMAIL_PARAMETER);
        try {
            UserService userService = (UserService) getService();
            Map<String, Object> sessionAttributes = content.getSessionAttributes();
            String locale = (String) sessionAttributes.get(SessionAttribute.LOCALE);
            ResourceBundle resourceBundle = ResourceBundle.getBundle("pagecontent", new Locale(locale));
            String subject = resourceBundle.getString(MESSAGE_MAIL_SUBJECT);
            String text = resourceBundle.getString(MESSAGE_MAIL_TEXT);
            isSent = userService.recoverPassword(email, subject, text);
        } catch (ServiceException e) {
            LOG.error(ERROR_RECOVER_PASSWORD, email, e);
            throw new CommandException(ERROR_RECOVER_PASSWORD, email, e);
        }
        putMessageIntoSession(content, isSent, PASSWORD_CHANGE_SUCCESS_MESSAGE, PASSWORD_CHANGE_FAIL_MESSAGE);
        String targetPagePath = null;
        if (isSent) {
            targetPagePath = LOGIN_PAGE_PATH;
        } else {
            targetPagePath = RECOVERY_PAGE_PATH;
        }
        return new ActionResult(targetPagePath, NavigationType.REDIRECT);
    }
}
