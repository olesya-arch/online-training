package epam.jwd.online_training.dao;

public class DAOManager {

    private static final UserDaoImpl USER_DAO = new UserDaoImpl();

    private DAOManager() {
    }

    public static UserDaoImpl getUserDao() { return USER_DAO; }



}
