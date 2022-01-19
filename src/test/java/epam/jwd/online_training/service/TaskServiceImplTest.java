package epam.jwd.online_training.service;

import epam.jwd.online_training.dto.TaskDto;
import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.entity.TaskReview;
import epam.jwd.online_training.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServiceImplTest {

    private TaskServiceImpl service;
    private Task task;
    private int id;
    private String title;
    private String description;
    private int courseId;
    private TaskDto taskDto;
    List<Task> taskList;
    List<TaskDto> taskDtoList;
    private int userId;

    @Before
    public void setUp() throws Exception {
        service = mock(TaskServiceImpl.class);
        task = new Task(1, "Task 1", "Write 10 sentences with 10 new words.", 3);
        id = 1;
        title = "Task 1";
        description = "Write 10 sentences with 10 new words.";
        courseId = 3;
        taskList = new ArrayList<>();
        taskList.add(task);
        taskDto = new TaskDto(task, new TaskReview(2, 1, "Done", "Cood job", 9));
        taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto);
        userId = 3;
    }

    @Test
    public void addTask() throws ServiceException {
        boolean expectedResult = true;
        when(service.addTask(courseId, title, description)).thenReturn(expectedResult);
        boolean actualResult = service.addTask(courseId, title, description);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getTaskByCourseId() throws ServiceException {
        List<Task> expectedResult = taskList;
        when(service.getTaskByCourseId(courseId)).thenReturn(expectedResult);
        List<Task> actualResult = service.getTaskByCourseId(courseId);
        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void getReceivedTasks() throws ServiceException {
        List<TaskDto> expectedResult = taskDtoList;
        when(service.getReceivedTasks(userId)).thenReturn(expectedResult);
        List<TaskDto> actualResult = service.getReceivedTasks(userId);
        assertEquals(actualResult, expectedResult);
    }
}