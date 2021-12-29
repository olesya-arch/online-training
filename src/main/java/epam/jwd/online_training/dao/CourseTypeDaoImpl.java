package epam.jwd.online_training.dao;

import epam.jwd.online_training.connection.ProxyConnection;
import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseTypeDaoImpl extends AbstractDao implements CourseTypeDao {

    private static final Logger LOG = LogManager.getLogger(CourseTypeDaoImpl.class);
    private static final String COURSE_TYPE_DAO_EXCEPTION = "Failed getting list of all course types from dao.";
    private static final String FIND_ALL_TYPES =
            "select id_type, " +
                    "category " +
                    "from course_type";
    @Override
    public List<CourseType> showAllTypes() throws DaoException {
        List<CourseType> courseTypeList = new ArrayList<>();
        ProxyConnection proxyConnection = connectionThreadLocal.get();
        try(PreparedStatement statement = proxyConnection.prepareStatement(FIND_ALL_TYPES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseType courseType = ResultSetParser.createCourseType(resultSet);
                courseTypeList.add(courseType);
            }
        } catch (SQLException e) {
            LOG.error(COURSE_TYPE_DAO_EXCEPTION, e);
            throw new DaoException(COURSE_TYPE_DAO_EXCEPTION, e);
        }
        return courseTypeList;
    }
}
