package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.exception.CommandException;

public class GetPageCommand extends Command {

    private static final String PROPERTY_PREFIX = "path.page.";
    private static final String EXPECTED_PAGE_PARAMETER = "expectedPage";

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        String expectedPage = content.getSingleRequestParameter(EXPECTED_PAGE_PARAMETER);
        String pageKey = PROPERTY_PREFIX + expectedPage;
        String page = PagePathManager.getProperty(pageKey);
        return new ActionResult(page, NavigationType.FORWARD);
    }
}
