package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.TaskReviewDto;
import epam.jwd.online_training.entity.TaskReview;
import epam.jwd.online_training.entity.User;
import epam.jwd.online_training.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskReviewServiceImplTest {

    private TaskReviewServiceImpl service;
    private int userId;
    private int taskId;
    private String answer;
    private String review;
    private int mark;
    private TaskReviewDto taskReviewDto;
    List<TaskReviewDto> taskReviewDtoList;

    @Before
    public void setUp() throws Exception {
        service = mock(TaskReviewServiceImpl.class);
        userId = 11;
        taskId = 22;
        answer = "Done";
        review = "Good job";
        mark = 8;
        taskReviewDto = new TaskReviewDto(new TaskReview(11, 22, "Done", "Good job", 8),
                new User());
        taskReviewDtoList = new ArrayList<>();
        taskReviewDtoList.add(taskReviewDto);
    }

    @Test
    public void sendAnswer() throws ServiceException {
        boolean expectedResult = true;
        when(service.sendAnswer(taskId, userId, answer)).thenReturn(expectedResult);
        boolean actualResult = service.sendAnswer(taskId, userId, answer);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void sendReview() throws ServiceException {
        boolean expectedResult = true;
        when(service.sendReview(userId, taskId, review, mark)).thenReturn(expectedResult);
        boolean actualResult = service.sendReview(userId, taskId, review, mark);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getAllReviewsByTaskId() throws ServiceException {
        List<TaskReviewDto> expectedResult = taskReviewDtoList;
        when(service.getAllReviewsByTaskId(taskId)).thenReturn(expectedResult);
        List<TaskReviewDto> actualResult = service.getAllReviewsByTaskId(taskId);
        assertEquals(actualResult, expectedResult);
    }
}