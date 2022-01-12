package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.command.bundle.PagePathManager;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.dto.StatisticDto;
import epam.jwd.online_training.exception.CommandException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.service.ServiceManager;
import epam.jwd.online_training.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetStatisticCommand extends Command {

    private static final Logger LOG = LogManager.getLogger(GetStatisticCommand.class);
    private static final String STATISTIC_COMMAND_EXCEPTION = "Exception occurred in statistic command.";
    private static final String STATISTIC_PATH = PagePathManager.getProperty("path.page.statistic");
    private static final String STATISTIC_PARAM = "getStatistic";

    public GetStatisticCommand() {
        super(ServiceManager.getUserService());
    }

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        try {
            UserService userService = (UserService) getService();
            StatisticDto statisticDto = userService.getStatistic();
            content.setSessionAttributes(STATISTIC_PARAM, statisticDto);
        } catch (ServiceException e) {
            LOG.error(STATISTIC_COMMAND_EXCEPTION, e);
            throw new CommandException(STATISTIC_COMMAND_EXCEPTION, e);
        }
        return new ActionResult(STATISTIC_PATH, NavigationType.FORWARD);
    }
}
