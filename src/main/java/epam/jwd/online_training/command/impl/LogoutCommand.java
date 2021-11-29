package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.logic.ServiceManager;

public class LogoutCommand extends Command {

    private static final String INVALIDATE_SESSION_MARKER = "invalidate";
    private static final String LOGIN_PAGE_PATH = "/controller?command=getPage&expectedPage=login";

    public LogoutCommand() { super(ServiceManager.getUserService()); }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        content.cleanSession();
        content.setSessionAttributes(INVALIDATE_SESSION_MARKER, Boolean.TRUE);
        return new ActionResult(LOGIN_PAGE_PATH, NavigationType.REDIRECT);
    }
}
