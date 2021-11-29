package epam.jwd.online_training.dao;

import epam.jwd.online_training.constant.EntityAttribute;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.entity.UserRole;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetParser {

    private static final Logger LOG = LogManager.getLogger(ResultSetParser.class);
    private static final String CANNOT_CREATE_USER = "Cannot create a user! ";

    static User createUser(ResultSet resultSet) throws DaoException {
        User user = null;
        try {
            Integer id = resultSet.getInt(EntityAttribute.USER_ID);
            String email = resultSet.getString(EntityAttribute.USER_EMAIL);
            String password = resultSet.getString(EntityAttribute.USER_PASSWORD);
            String firstName = resultSet.getString(EntityAttribute.USER_FIRST_NAME);
            String lastName = resultSet.getString(EntityAttribute.USER_LAST_NAME);
            String roleName = resultSet.getString(EntityAttribute.USER_ROLE);
            String roleValue = roleName.toUpperCase();
            UserRole role = UserRole.valueOf(roleValue);

            user = new User(id, email, password, firstName, lastName, role);

        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_USER, e);
            throw new DaoException(CANNOT_CREATE_USER, e);
        }
        return user;
    }
}
