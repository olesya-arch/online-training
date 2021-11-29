package epam.jwd.online_training.tag;

import epam.jwd.online_training.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeTag extends TagSupport {

    private static final Logger LOG = LogManager.getLogger(WelcomeTag.class);
    private static final String MESSAGE_BEGIN_KEY = "label.main_page.begin";
    private static final String MESSAGE_END_ADMIN_KEY = "label.main_page.end-admin";
    private static final String MESSAGE_END_TEACHER_KEY = "label.main_page.end-teacher";
    private static final String MESSAGE_END_STUDENT_KEY = "label.main_page.end-student";
    private static final String MESSAGE_END_GUEST_KEY = "label.main_page.end-guest";
    private static final String OUTPUT_FORMAT_BEGIN = "<hr><h2 align=\"center\">";
    private static final String OUTPUT_FORMAT_END = "</h2><hr>";

    private UserRole role;
    private String locale;
    private String fullName;

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int doStartTag() throws JspException {
        String language = locale.substring(0, locale.indexOf("_"));
        String country = locale.substring(locale.indexOf("_")+ 1);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("pagecontent", new Locale(language, country));
        String messageBegin = resourceBundle.getString(MESSAGE_BEGIN_KEY);
        String messageEnd = null;

        if (role == UserRole.ADMIN) {
            messageEnd = resourceBundle.getString(MESSAGE_END_ADMIN_KEY);
        } else if (role == UserRole.TEACHER) {
            messageEnd = resourceBundle.getString(MESSAGE_END_TEACHER_KEY);
        } else if (role == UserRole.STUDENT) {
            messageEnd = resourceBundle.getString(MESSAGE_END_STUDENT_KEY);
        } else {
            messageEnd = resourceBundle.getString(MESSAGE_END_GUEST_KEY);
        }
        String outputMessage = messageBegin + fullName + messageEnd;
        try {
            JspWriter writer = pageContext.getOut();
            writer.write(OUTPUT_FORMAT_BEGIN + outputMessage + OUTPUT_FORMAT_END);
        } catch (IOException e) {
            LOG.error("Failed to send an email:\n" + e.getMessage(), e);
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
