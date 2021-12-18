package epam.jwd.online_training.command.bundle;

import java.util.ResourceBundle;

public class PagePathManager {

    private static final String CONFIG_FILE_PATH = "config";

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(CONFIG_FILE_PATH);

    public PagePathManager() {
    }

    public static String getProperty(String key) { return resourceBundle.getString(key); }
}
