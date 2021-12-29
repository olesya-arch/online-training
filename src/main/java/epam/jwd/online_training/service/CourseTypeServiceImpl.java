package epam.jwd.online_training.service;

import epam.jwd.online_training.dao.CourseTypeDaoImpl;
import epam.jwd.online_training.dao.DAOManager;
import epam.jwd.online_training.dao.TransactionManager;
import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.exception.DaoException;
import epam.jwd.online_training.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CourseTypeServiceImpl implements CourseTypeService {
    private static final Logger LOG = LogManager.getLogger(CourseTypeServiceImpl.class);
    private static final String COURSE_TYPE_SERVICE_EXCEPTION = "Failed showing all course types in service.";
    private static CourseTypeDaoImpl courseTypeDao = DAOManager.getCourseTypeDao();

    @Override
    public List<CourseType> getAllLanguages() throws ServiceException {
        List<CourseType> courseTypeList;
        try(TransactionManager tm = TransactionManager.launchQuery(courseTypeDao)) {
            courseTypeList = courseTypeDao.showAllTypes();
        } catch (SQLException | DaoException e) {
            LOG.error(COURSE_TYPE_SERVICE_EXCEPTION, e);
            throw new ServiceException(COURSE_TYPE_SERVICE_EXCEPTION, e);
        }
        return courseTypeList;
    }
}
