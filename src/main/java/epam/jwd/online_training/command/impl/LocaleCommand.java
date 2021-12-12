package epam.jwd.online_training.command.impl;

import epam.jwd.online_training.command.Command;
import epam.jwd.online_training.constant.SessionAttribute;
import epam.jwd.online_training.content.ActionResult;
import epam.jwd.online_training.content.NavigationType;
import epam.jwd.online_training.content.RequestContent;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.entity.UserRole;
import epam.jwd.online_training.exception.CommandException;

import java.util.Map;

public class LocaleCommand extends Command {

    private static final String EN_LOCALE_MARKER = "EN";
    private static final String EN_LOCALE_ATTRIBUTE = "en_US";
    private static final String RU_LOCALE_ATTRIBUTE = "ru_RU";
    private static final String STUDENT_PAGE = "/controller?command=getPage&expectedPage=studentpage";
    private static final String TEACHER_PAGE = "/controller?command=getPage&expectedPage=teacherpage";
    private static final String ADMIN_PAGE = "/controller?command=getPage&expectedPage=adminpage";
    private static final String EMPTY_COMMAND_PATTERN = "/controller";

    @Override
    public ActionResult execute(RequestContent content) throws CommandException {
        String requestLocale = content.getSingleRequestParameter(SessionAttribute.LOCALE);
        String locale;
        if (EN_LOCALE_MARKER.equals(requestLocale)) {
            locale = EN_LOCALE_ATTRIBUTE;
        } else {
            locale = RU_LOCALE_ATTRIBUTE;
        }
        content.setSessionAttributes(SessionAttribute.LOCALE, locale);

        String targetUrl = defineTargetPage(content);

        return new ActionResult(targetUrl, NavigationType.REDIRECT);
    }

    private String defineTargetPage(RequestContent content) {
        String currentUrl = content.getUrl();
        String targetUrl = null;
        if (currentUrl.endsWith(EMPTY_COMMAND_PATTERN)) {
            Map<String, Object> sessionAttributes = content.getSessionAttributes();
            User user = (User) sessionAttributes.get(SessionAttribute.USER);
            UserRole userRole = user.getRole();
            switch (userRole) {
                case STUDENT:
                    targetUrl = STUDENT_PAGE;
                    break;
                case TEACHER:
                    targetUrl = TEACHER_PAGE;
                    break;
                case ADMIN:
                    targetUrl = ADMIN_PAGE;
            }
        } else {
            targetUrl = currentUrl;
        }
        return targetUrl;
    }
}
