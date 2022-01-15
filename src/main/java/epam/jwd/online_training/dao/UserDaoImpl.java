package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ProxyConnection;
import epam.jwd.online_training.dto.StatisticDto;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.entity.UserRole;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);
    private static final String NOT_FOUND_USER_BY_ROLE_EXCEPTION =
            "Exception occurred trying to find user by role! ";
    private static final String NOT_FOUND_USER_BY_EMAIL_AND_PASSWORD_EXCEPTION =
            "Exception occurred trying to find user by email and password in DAO.";
    private static final String USER_NOT_FOUND_BY_EMAIL_EXCEPTION = "Not found user with email: {} ";
    private static final String FAIL_UPDATING_USER_PASSWORD_EXCEPTION = "Failed updating user password in DAO.";
    private static final String FAIL_DELETING_USER_BY_ID_EXCEPTION = "Failed deleting user by id in DAO.";
    private static final String ADDING_COURSE_TO_USER_EXCEPTION = "Exception occurred adding the course to user in DAO.";
    private static final String FAIL_ADDING_NEW_ACCOUNT_EXCEPTION = "Failed adding new account in DAO. ";
    private static final String FAIL_GETTING_STATISTIC_INFORMATION_EXCEPTION = "Failed getting statistic information in DAO.";
    private static final String FAIL_GETTING_COUNT_OF_PEOPLE_WHO_STUDY_DEFINITE_LANGUAGE_EXCEPTION =
            "Failed getting count of people who study definite language in DAO. ";

    private static final String USER_ID = "id_account";
    private static final String USER_STATUS_IS_DELETED = "status_is_deleted";
    private static final String DELETE_TEACHER_BY_ID =
            "CALL deleteTeacher(?);";

    private static final String FIND_ALL_USERS_BY_ROLE =
            "select id_account, " +
                    "e_mail, " +
                    "u_password, " +
                    "first_name, " +
                    "last_name, " +
                    "account_role, " +
                    "status_is_deleted " +
                    "from user_account " +
                    "where account_role=? " +
                    "and status_is_deleted!=1";

    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD =
            "select id_account, " +
                    "e_mail, " +
                    "u_password, " +
                    "first_name, " +
                    "last_name, " +
                    "account_role, " +
                    "status_is_deleted " +
                    "from user_account " +
                    "where e_mail=? and u_password=?";

    private static final String CHECK_USER_BY_EMAIL =
            "select id_account, status_is_deleted " +
                    "from user_account " +
                    "where e_mail=?";

    private static final String UPDATE_USER_PASSWORD =
            "update user_account " +
                    "set u_password=? " +
                    "where e_mail=?";

    private static final String ADD_COURSE_TO_USER =
            "insert into course_enrolment " +
                    "(c_e_course_id, c_e_student_id) " +
                    "values (?,?)";

    private static final String ADD_USER_ACCOUNT =
            "insert into user_account " +
                    "(e_mail, " +
                    "u_password, " +
                    "first_name, " +
                    "last_name, " +
                    "account_role) " +
                    "values (?,?,?,?,?)";

    private static final String GET_COUNT_OF_PEOPLE_WHO_STUDY_THAT_LANGUAGE =
            "select count(ua.id_account) from user_account as ua " +
                    "join course_enrolment as ce " +
                    "on ua.id_account = ce.c_e_student_id " +
                    "join course as c " +
                    "on ce.course_id = c.id_course " +
                    "join course_type as ct " +
                    "on c.course_type = ct.id_type " +
                    "where ct.category in (?)";

    private static final String GET_STATISTIC =
            "select count(id_account) " +
                    "from user_account " +
                    "union all " +
                    "select count(task_id) " +
                    "from task " +
                    "union all " +
                    "select count(id_course) " +
                    "from course " +
                    "union all " +
                    "select count(id_account) " +
                    "from user_account " +
                    "where account_role = 'teacher' " +
                    "union all " +
                    "select count(distinct category) " +
                    "from course_type ";

    @Override
    public User findUserByEmailAndPassword(String email, String password) throws DaoException {
        User user = null;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = ResultSetParser.createUser(resultSet);
            }
        } catch (SQLException e) {
            LOG.error(NOT_FOUND_USER_BY_EMAIL_AND_PASSWORD_EXCEPTION, e);
            throw new DaoException(NOT_FOUND_USER_BY_ROLE_EXCEPTION, e);
        }
        return user;
    }

    @Override
    public boolean checkUserByEmail(String email) throws DaoException {
        boolean isFound = false;
        int userId = 0;
        boolean isDeleted = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement preparedStatement = proxyConnection.prepareStatement(CHECK_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt(USER_ID);
                isDeleted = (resultSet.getInt(USER_STATUS_IS_DELETED) == 1);
            }
        } catch (SQLException e) {
            LOG.error(USER_NOT_FOUND_BY_EMAIL_EXCEPTION, email, e);
            throw new DaoException(USER_NOT_FOUND_BY_EMAIL_EXCEPTION, email, e);
        }
        if (userId != 0 && !isDeleted) {
            isFound = true;
        }
        return isFound;
    }

    @Override
    public boolean updateUserPassword(String email, String password) throws DaoException {
        boolean isUpdated = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(UPDATE_USER_PASSWORD)) {
            statement.setString(1,password);
            statement.setString(2, email);
            if (statement.executeUpdate() != 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            LOG.error(FAIL_UPDATING_USER_PASSWORD_EXCEPTION, e);
            throw new DaoException(FAIL_UPDATING_USER_PASSWORD_EXCEPTION, e);
        }
        return isUpdated;
    }

    @Override
    public boolean addUserAccount(String email, String password, String firstName, String lastName, String role)
            throws DaoException {
        boolean isAccountAdded = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(ADD_USER_ACCOUNT)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3,firstName);
            statement.setString(4, lastName);
            String userRole;
            if (role.equalsIgnoreCase("teacher")) {
                userRole = UserRole.TEACHER.toString();
            } else {
                userRole = UserRole.STUDENT.toString();
            }
            String userRoleLowerCase = userRole.toLowerCase();
            statement.setString(5, userRoleLowerCase);
            isAccountAdded = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            LOG.error(FAIL_ADDING_NEW_ACCOUNT_EXCEPTION, e);
            throw new DaoException(FAIL_ADDING_NEW_ACCOUNT_EXCEPTION, e);
        }
        return isAccountAdded;
    }

    @Override
    public boolean joinCourse(int courseId, int userId) throws DaoException {
        boolean isJoined = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(ADD_COURSE_TO_USER)) {
            statement.setInt(1, courseId);
            statement.setInt(2, userId);
            int result = statement.executeUpdate();
            isJoined = (result != 0);
        } catch (SQLException e) {
            LOG.error(ADDING_COURSE_TO_USER_EXCEPTION, e);
            throw new DaoException(ADDING_COURSE_TO_USER_EXCEPTION, e);
        }
        return isJoined;
    }

    @Override
    public List<User> findAllTeachers() throws DaoException {
        List<User> teachersList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_ALL_USERS_BY_ROLE)) {
        String teacherRoleLine = UserRole.TEACHER.toString();
        String role = teacherRoleLine.toLowerCase();
        statement.setString(1, role);
        ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            User teacher = ResultSetParser.createUser(resultSet);
            teachersList.add(teacher);
            }
        } catch (SQLException e) {
            LOG.error(NOT_FOUND_USER_BY_ROLE_EXCEPTION, e);
            throw new DaoException(NOT_FOUND_USER_BY_ROLE_EXCEPTION, e);
        }
        return teachersList;
    }

    @Override
    public boolean deleteUser(int userId) throws DaoException {
        boolean isDeleteSuccessfully = false;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(CallableStatement statement = proxyConnection.prepareCall(DELETE_TEACHER_BY_ID)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
            isDeleteSuccessfully = true;
        } catch (SQLException e) {
            LOG.error(FAIL_DELETING_USER_BY_ID_EXCEPTION, e);
            throw new DaoException(FAIL_DELETING_USER_BY_ID_EXCEPTION, e);
        }
        return isDeleteSuccessfully;
    }

    @Override
    public StatisticDto getStatistic() throws DaoException {
        StatisticDto statisticDto = null;

        int usersCount = 0;
        int tasksCount = 0;
        int coursesCount = 0;
        int teachersCount = 0;
        int courseTypesCount = 0;
        int englishLanguageCount = 0;
        int germanLanguageCount = 0;
        int frenchLanguageCount = 0;
        int latvianLanguageCount = 0;
        int chineseLanguageCount = 0;

        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement= proxyConnection.prepareStatement(GET_STATISTIC)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            usersCount = resultSet.getInt(1);
            resultSet.next();
            tasksCount = resultSet.getInt(1);
            resultSet.next();
            coursesCount = resultSet.getInt(1);
            resultSet.next();
            teachersCount = resultSet.getInt(1);
            resultSet.next();
            courseTypesCount = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error(FAIL_GETTING_STATISTIC_INFORMATION_EXCEPTION,e);
            throw new DaoException(FAIL_GETTING_STATISTIC_INFORMATION_EXCEPTION,e);
        }

        englishLanguageCount = getCountOfUsersWhoStudyThatLanguage("english");
        germanLanguageCount = getCountOfUsersWhoStudyThatLanguage("german");
        frenchLanguageCount = getCountOfUsersWhoStudyThatLanguage("french");
        latvianLanguageCount = getCountOfUsersWhoStudyThatLanguage("latvian");
        chineseLanguageCount = getCountOfUsersWhoStudyThatLanguage("chinese");

        statisticDto = new StatisticDto(usersCount, tasksCount, coursesCount, teachersCount, courseTypesCount,
                englishLanguageCount, germanLanguageCount, frenchLanguageCount, latvianLanguageCount, chineseLanguageCount);

        return statisticDto;
    }

    @Override
    public int getCountOfUsersWhoStudyThatLanguage(String language) throws DaoException {
        int count = 0;
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(GET_COUNT_OF_PEOPLE_WHO_STUDY_THAT_LANGUAGE)) {
            statement.setString(1, language);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            LOG.error(FAIL_GETTING_COUNT_OF_PEOPLE_WHO_STUDY_DEFINITE_LANGUAGE_EXCEPTION, e);
            throw new DaoException(FAIL_GETTING_COUNT_OF_PEOPLE_WHO_STUDY_DEFINITE_LANGUAGE_EXCEPTION, e);
        }
        return count;
    }
}
