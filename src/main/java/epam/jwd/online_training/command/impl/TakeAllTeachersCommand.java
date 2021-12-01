package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.constant.EntityAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TakeAllTeachersCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(TakeAllTeachersCommand.class);
    private static final String ALL_TEACHERS_PATH = PagePathManager.getProperty("path.page.all_teachers");
    private static final String SHOWING_ALL_TEACHERS_COMMAND_EXCEPTION =
            "Exception occurred during showing all teachers command ";

    public TakeAllTeachersCommand() { super(ServiceManager.getUserService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        try {
            UserService userReceiver = (UserService) getService();
            List<User> teachersList = userReceiver.getAllTeachers();
            content.setSessionAttributes(EntityAttribute.ALL_TEACHERS_PARAM, teachersList);
        } catch (ServiceException e) {
            LOG.error(SHOWING_ALL_TEACHERS_COMMAND_EXCEPTION, e);
            throw new CommandException(SHOWING_ALL_TEACHERS_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(ALL_TEACHERS_PATH, NavigationType.FORWARD);
    }
}
