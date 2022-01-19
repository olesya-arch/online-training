package epam.jwd.online_training.service;

import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.entity.Language;
import epam.jwd.online_training.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseTypeServiceImplTest {

    private CourseTypeServiceImpl service;
    private CourseType courseType;
    private int id;
    private String language;
    List<CourseType> courseTypeList;

    @Before
    public void setUp() throws Exception {
        service = mock(CourseTypeServiceImpl.class);
        courseType = new CourseType(7, Language.CHINESE);
        id = 7;
        language = "Chinese";
        courseTypeList = new ArrayList<>();
        courseTypeList.add(courseType);
    }

    @Test
    public void getAllLanguages() throws ServiceException {
        List<CourseType> expectedResult = courseTypeList;
        when(service.getAllLanguages()).thenReturn(expectedResult);
        List<CourseType> actualResult = service.getAllLanguages();
        assertEquals(actualResult, expectedResult);
    }
}