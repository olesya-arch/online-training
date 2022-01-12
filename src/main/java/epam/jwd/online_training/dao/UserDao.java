package epam.jwd.online_training.dao;

import epam.jwd.online_training.dto.StatisticDto;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface UserDao {
    
    User findUserByEmailAndPassword(String email, String password) throws DaoException;
    boolean checkUserByEmail(String email) throws  DaoException;
    boolean updateUserPassword(String login, String password) throws DaoException;
    boolean addUserAccount(String email, String password, String firstName, String lastName, String role)
            throws DaoException;
    boolean joinCourse(int courseId, int userId) throws DaoException;
    List<User> findAllTeachers() throws DaoException;
    boolean deleteUser(int userId) throws DaoException;
    StatisticDto getStatistic() throws DaoException;
    int getCountOfUsersWhoStudyThatLanguage(String language) throws DaoException;
}
