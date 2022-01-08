package epam.jwd.online_training.dto;

import epam.jwd.online_training.entity.Task;
import epam.jwd.online_training.entity.TaskReview;

import java.io.Serializable;

public class TaskDto implements Serializable {

    private static final long serialVersionUID = 7048436975230701193L;
    private Integer id;
    private String title;
    private String description;
    private int courseId;
    private TaskReview review;

    public TaskDto() {
    }

    public TaskDto(Task task, TaskReview review) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.courseId = task.getCourseId();
        this.review = review;
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

    public TaskReview getReview() {
        return review;
    }

    public void setReview(TaskReview review) {
        this.review = review;
    }
}
