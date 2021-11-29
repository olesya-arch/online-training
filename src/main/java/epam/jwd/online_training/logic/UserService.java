package epam.jwd.online_training.logic;

import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface UserService extends Service {

    User login(String loginInput, String passwordInput) throws ServiceException;
    boolean isEmailTaken(String emailInput) throws ServiceException;
    boolean signUp(String email, String password, String firstName, String lastName, String role)
            throws ServiceException;
    boolean joinCourse(int courseId, int userId) throws ServiceException;
    List<User> getAllTeachers() throws ServiceException;
    boolean deleteUser(int userId) throws ServiceException;
    boolean recoverPassword(String email, String subject, String text) throws ServiceException;
}
