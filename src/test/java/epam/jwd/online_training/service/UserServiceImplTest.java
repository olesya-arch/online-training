package epam.jwd.online_training.service;

import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.entity.UserRole;
import epam.jwd.online_training.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserServiceImpl service;
    private User user;
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private List<User> users;
    private String text;
    private String subject;
    private int courseId;

    @Before
    public void setUp() throws Exception {
        service = mock(UserServiceImpl.class);
        user = new User(1, "sar555@gmail.com", "sar555",
                "Sarah", "White", UserRole.TEACHER);
        id = 1;
        email = "sar5555@gmail.com";
        password = "sar555";
        firstName = "Sarah";
        lastName = "White";
        role = "User";
        users = new ArrayList<>();
        users.add(user);
        text = "new password";
        subject = "send password";
        courseId = 15;
    }

    @Test
    public void login() throws ServiceException {
        User expectedResult = user;
        when(service.login(email, password)).thenReturn(expectedResult);
        User actualResult = service.login(email, password);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void isEmailTaken() throws ServiceException {
        boolean expectedResult = true;
        when(service.isEmailTaken(email)).thenReturn(expectedResult);
        boolean actualResult = service.isEmailTaken(email);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void signUp() throws ServiceException {
        boolean expectedResult = true;
        when(service.signUp(email, password, firstName, lastName, role)).thenReturn(expectedResult);
        boolean actualResult = service.signUp(email, password, firstName, lastName, role);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getAllTeachers() throws ServiceException {
        List<User> expectedResult = users;
        when(service.getAllTeachers()).thenReturn(expectedResult);
        List<User> actualResult = service.getAllTeachers();
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void deleteUser() throws ServiceException {
        boolean expectedResult = true;
        when(service.deleteUser(id)).thenReturn(expectedResult);
        boolean actualResult = service.deleteUser(id);
        assertEquals(actualResult, expectedResult);
    }
    @Test
    public void recoverPassword() throws ServiceException {
        boolean expectedResult = true;
        when(service.recoverPassword(email, subject, text)).thenReturn(expectedResult);
        boolean actualResult = service.recoverPassword(email, subject, text);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void joinCourse() throws ServiceException {
        boolean expectedResult = true;
        when(service.joinCourse(courseId, id)).thenReturn(expectedResult);
        boolean actualResult = service.joinCourse(courseId, id);
        assertEquals(actualResult, expectedResult);
    }


}