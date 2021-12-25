package epam.jwd.online_training.service;

import epam.jwd.online_training.dao.DAOManager;
import epam.jwd.online_training.dao.TransactionManager;
import epam.jwd.online_training.dao.UserDaoImpl;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.DaoException;
import epam.jwd.online_training.exception.ServiceException;
import epam.jwd.online_training.util.MailSender;
import epam.jwd.online_training.util.PasswordCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import static epam.jwd.online_training.util.PasswordEncoder.encodePassword;

public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);
    private static UserDaoImpl userDao = DAOManager.getUserDao();

    private static final String FAIL_TO_GET_ALL_TEACHERS = "Failed getting instance all teachers from dao. ";
    private static final String FAIL_IN_LOGIN_PROCESS = "Failed in login process. ";
    private static final String FAIL_IN_PROCESS_OF_DELETING_USER = "Failed in process deleting user in service. ";
    private static final String FAIL_IN_PROCESS_OF_CHECKING_EMAIL = "Failed checking email availability. ";
    private static final String FAIL_IN_SIGN_UP = "Failed creating a new user during sign up. ";
    private static final String FAIL_IN_PROCESS_OF_JOINING_COURSE = "Failed joining the course. ";
    private static final String FAIL_UPDATING_USER_PASSWORD = "Failed updating user password. ";

    @Override
    public User login(String emailInput, String passwordInput) throws ServiceException {
        User user = null;
        String encodePassword = encodePassword(passwordInput);
        try(TransactionManager tm = TransactionManager.launchQuery(userDao)) {
            if (userDao.checkUserByEmail(emailInput)) {
                user = userDao.findUserByEmailAndPassword(emailInput, encodePassword);
            }
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_IN_LOGIN_PROCESS, e);
            throw new ServiceException(FAIL_IN_LOGIN_PROCESS, e);
        }
        return user;
    }

    @Override
    public boolean isEmailTaken(String emailInput) throws ServiceException {
        boolean isTaken = false;
        try(TransactionManager tm = TransactionManager.launchTransaction(userDao)) {
            isTaken = userDao.checkUserByEmail(emailInput);
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_IN_PROCESS_OF_CHECKING_EMAIL, e);
            throw new ServiceException(FAIL_IN_PROCESS_OF_CHECKING_EMAIL, e);
        }
        return isTaken;
    }

    @Override
    public boolean signUp(String email, String password, String firstName, String lastName, String role) throws ServiceException {
        boolean isRegistered = false;
        String encodePassword = encodePassword(password);
        try(TransactionManager tm = TransactionManager.launchTransaction(userDao)) {
            isRegistered = userDao.addUserAccount(email, encodePassword, firstName, lastName, role);
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_IN_SIGN_UP, e);
            throw new ServiceException(FAIL_IN_SIGN_UP, e);
        }
        return isRegistered;
    }

    @Override
    public boolean joinCourse(int courseId, int userId) throws ServiceException {
        boolean isJoined = false;
        try(TransactionManager tm = TransactionManager.launchTransaction(userDao)) {
            isJoined = userDao.joinCourse(courseId, userId);
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_IN_PROCESS_OF_JOINING_COURSE, e);
            throw new ServiceException(FAIL_IN_PROCESS_OF_JOINING_COURSE,e);
        }
        return isJoined;
    }

    @Override
    public List<User> getAllTeachers() throws ServiceException {
        List<User> teachersList = null;
        try(TransactionManager transactionManager = TransactionManager.launchQuery(userDao)) {
            teachersList = userDao.findAllTeachers();
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_TO_GET_ALL_TEACHERS, e);
            throw new ServiceException(FAIL_TO_GET_ALL_TEACHERS, e);
        }
        return teachersList;
    }

    @Override
    public boolean deleteUser(int userId) throws ServiceException {
        boolean isDeletedSuccessfully = false;
        try(TransactionManager tm = TransactionManager.launchTransaction(userDao)) {
            isDeletedSuccessfully = userDao.deleteUser(userId);
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_IN_PROCESS_OF_DELETING_USER, e);
            throw new ServiceException(FAIL_IN_PROCESS_OF_DELETING_USER, e);
        }
        return isDeletedSuccessfully;
    }

    @Override
    public boolean recoverPassword(String email, String subject, String text) throws ServiceException {
        boolean isSent = false;
        String password = PasswordCreator.createPassword();
        try(TransactionManager tm = TransactionManager.launchTransaction(userDao)) {
            boolean isUserExist = userDao.checkUserByEmail(email);
            if (isUserExist) {
                String encodePassword = encodePassword(password);
                userDao.updateUserPassword(email, encodePassword);
                MailSender mailSender = new MailSender();
                String message = text + password;
                mailSender.send(subject, message, email);
                isSent = true;
            }
        } catch (SQLException | DaoException e) {
            LOG.error(FAIL_UPDATING_USER_PASSWORD, e);
            throw new ServiceException(FAIL_UPDATING_USER_PASSWORD, e);
        }
        return isSent;
    }
}
