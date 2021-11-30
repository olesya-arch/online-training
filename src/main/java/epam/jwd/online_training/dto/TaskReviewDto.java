package epam.jwd.online_training.dto;

import epam.jwd.online_training.entity.TaskReview;
import epam.jwd.online_training.entity.User;

import java.io.Serializable;

public class TaskReviewDto implements Serializable {

    private static final long serialVersionUID = 4060783043391670430L;
    private Integer studentId;
    private Integer taskId;
    private String teacherComment;
    private int mark;
    private String firstName;
    private String lastName;

    public TaskReviewDto() {
    }

    public TaskReviewDto(TaskReview taskReview, User user) {
        this.studentId = taskReview.getId();
        this.taskId = taskReview.getTaskId();
        this.teacherComment = taskReview.getTeacherComment();
        this.mark = taskReview.getMark();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public Integer getId() {
        return studentId;
    }

    public void setId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
