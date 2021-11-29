package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.constant.SighUpAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.logic.ServiceManager;
import epam.jwd.online_training.logic.UserService;
import epam.jwd.online_training.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentSignUpCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(StudentSignUpCommand.class);
    private static final String ERROR_DURING_SIGN_UP = "Problem occurred during user sign up.";
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";
    private static final String REGISTRATION_PAGE_PATH = "/controller?command=getPage&expectedPage=registration";

    private static final String MESSAGE_EMAIL_INVALID = "message.email.error-invalid";
    private static final String MESSAGE_EMAIL_TAKEN = "message.email.error-taken";
    private static final String MESSAGE_LAST_NAME_INVALID = "message.lastName.error";
    private static final String MESSAGE_FIRST_NAME_INVALID = "message.firstName.error";
    private static final String MESSAGE_PASSWORD_INVALID = "message.password.error";
    private static final String MESSAGE_PASSWORD_REPEAT_INVALID = "message.password.repeat.error";
    private static final String MESSAGE_SIGN_UP_SUCCESS = "message.sign-up-success";
    private static final String MESSAGE_SIGN_UP_FAIL = "message.sing-up-error";
    private static final String USER_ROLE = "student";

    private String email;
    private String password;
    private String checkPassword;
    private String firstName;
    private String lastName;
    private String signUpFailMessage = MESSAGE_SIGN_UP_FAIL;

    public StudentSignUpCommand() { super(ServiceManager.getUserService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        email = content.getSingleRequestParameter(SighUpAttribute.EMAIL_PARAMETER);
        password = content.getSingleRequestParameter(SighUpAttribute.PASSWORD_PARAMETER);
        checkPassword = content.getSingleRequestParameter(SighUpAttribute.CHECK_PASSWORD_PARAMETER);
        firstName = content.getSingleRequestParameter(SighUpAttribute.FIRST_NAME_PARAMETER);
        lastName = content.getSingleRequestParameter(SighUpAttribute.LAST_NAME_PARAMETER);

        boolean isRegistered = false;
        UserService userService = (UserService) getService();

        try {
            boolean isParametersValid = isParametersValid(userService);
            if (isParametersValid) {
                isRegistered = userService.signUp(email, password, firstName, lastName, USER_ROLE);
            }
            content.setSessionAttributes("failEmail", email);
            content.setSessionAttributes("failFirstName", firstName);
            content.setSessionAttributes("failLastName", lastName);

            putMessageIntoSession(content, isRegistered, MESSAGE_SIGN_UP_SUCCESS,signUpFailMessage);
        } catch (ServiceException e) {
            LOG.error(ERROR_DURING_SIGN_UP);
            throw new CommandException(ERROR_DURING_SIGN_UP);
        }
        String targetPagePath = null;
        if (isRegistered) {
            targetPagePath = LOGIN_PAGE_PATH;
        } else {
            targetPagePath = REGISTRATION_PAGE_PATH;
        }
        return new ActionResult(targetPagePath, NavigationType.REDIRECT);
    }

    private boolean isParametersValid(UserService userService) throws ServiceException {
        boolean isValid = false;
        if (!Validator.checkEmail(email)) {
            signUpFailMessage = MESSAGE_EMAIL_INVALID;
        } else if (!Validator.checkFirstName(firstName)) {
            signUpFailMessage = MESSAGE_FIRST_NAME_INVALID;
        } else if (!Validator.checkLastName(lastName)) {
            signUpFailMessage = MESSAGE_LAST_NAME_INVALID;
        } else if (!Validator.checkPassword(password)) {
            signUpFailMessage = MESSAGE_PASSWORD_INVALID;
        } else if (!Validator.checkPasswordRepeat(password, checkPassword)) {
            signUpFailMessage = MESSAGE_PASSWORD_REPEAT_INVALID;
        } else if (userService.isEmailTaken(email)) {
            signUpFailMessage = MESSAGE_EMAIL_TAKEN;
        } else {
            isValid = true;
        }
        return isValid;
    }
}
