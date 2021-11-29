package epam.jwd.online_training.util;

public class Validator {

    private static final String EMAIL_REG_EXP = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
    private static final String PASSWORD_REG_EXP = "^[a-zA-Z0-9]{4,20}";
    private static final String FIRST_NAME_REG_EXP = "^[А-ЯA-Z][a-яa-z]{2,30}";
    private static final String LAST_NAME_REG_EXP = "^[А-ЯA-Z][a-яa-z]{2,30}(-[А-ЯA-Z][a-яa-z]{2,30})?";

    public static boolean checkEmail(String email) { return (email !=null) && email.matches(EMAIL_REG_EXP); }

    public static boolean checkPassword(String password) {
        return (password != null) && password.matches(PASSWORD_REG_EXP);
    }

    public static boolean checkPasswordRepeat(String password, String passwordRepeat) {
        boolean match = false;
        if (passwordRepeat != null && password != null) {
            return password.equals(passwordRepeat);
        }
        return match;
    }

    public static boolean checkFirstName(String firstName) {
        return (firstName != null) && firstName.matches(FIRST_NAME_REG_EXP);
    }

    public static boolean checkLastName(String lastName) {
        return (lastName != null) && lastName.matches(LAST_NAME_REG_EXP);
    }
}
