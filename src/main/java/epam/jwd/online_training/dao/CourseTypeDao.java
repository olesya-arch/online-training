package epam.jwd.online_training.dao;

import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.exception.DaoException;

import java.util.List;

public interface CourseTypeDao {
    List<CourseType> showAllTypes() throws DaoException;
}
