package epam.jwd.online_training.dto;

import epam.jwd.online_training.entity.TaskReview;
import epam.jwd.online_training.entity.User;

import java.io.Serializable;

public class TaskReviewDto implements Serializable {

    private static final long serialVersionUID = 5422210569534407101L;
    private Integer studentId;
    private Integer taskId;
    private String answer;
    private String review;
    private int mark;
    private String firstName;
    private String lastName;

    public TaskReviewDto() {
    }

    public TaskReviewDto(TaskReview review, User user) {
        this.studentId = review.getId();
        this.taskId = review.getTaskId();
        this.answer = review.getAnswer();
        this.review= review.getReview();
        this.mark = review.getMark();
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

    public String getAnswer() { return answer; }

    public void setAnswer(String taskAnswer) { this.answer = taskAnswer; }

    public String getReview() {
        return review;
    }

    public void setReview(String teacherComment) {
        this.review = teacherComment;
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
