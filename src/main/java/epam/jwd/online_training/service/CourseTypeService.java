package epam.jwd.online_training.service;

import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.exception.ServiceException;

import java.util.List;

public interface CourseTypeService extends Service {
    List<CourseType> getAllLanguages() throws ServiceException;
}
