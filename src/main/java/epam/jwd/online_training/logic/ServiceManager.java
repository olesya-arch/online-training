package epam.jwd.online_training.logic;

public class ServiceManager {

    private static final UserService USER_SERVICE = new UserServiceImpl();

    public static UserService getUserService() { return USER_SERVICE; }
}
