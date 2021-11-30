package epam.jwd.online_training.dto;

import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.entity.TaskReview;

import java.io.Serializable;

public class TaskDto implements Serializable {

    private static final long serialVersionUID = 4117575010954027202L;
    private Integer id;
    private String title;
    private String description;
    private int courseId;
    TaskReview taskReview;

    public TaskDto() {
    }

    public TaskDto(Task task, TaskReview taskReview) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.courseId = task.getCourseId();
        this.taskReview = taskReview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public TaskReview getTaskReview() {
        return taskReview;
    }

    public void setTaskReview(TaskReview taskReview) {
        this.taskReview = taskReview;
    }
}
