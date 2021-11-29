package epam.jwd.online_training.command;

import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.logic.Service;

public abstract class Command {

    private static final String MESSAGE_SUCCESS_ATTRIBUTE = "actionSuccessful";
    private static final String MESSAGE_FAIL_ATTRIBUTE = "actionFail";
    private Service service;

    public Command() {
    }

    public Command(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public abstract ActionResult execute(RequestContent content) throws CommandException;

    protected void putMessageIntoSession(RequestContent content, boolean isAdded,
                                         String successMessageKey, String failMessageKey) {
        if (isAdded) {
            content.setSessionAttributes(MESSAGE_SUCCESS_ATTRIBUTE, successMessageKey);
        } else {
            content.setSessionAttributes(MESSAGE_FAIL_ATTRIBUTE, failMessageKey);
        }
    }
}
