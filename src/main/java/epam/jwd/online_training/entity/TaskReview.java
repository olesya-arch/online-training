package epam.jwd.online_training.entity;

import java.util.Objects;

public class TaskReview implements BaseEntity {

    private static final long serialVersionUID = 3837244967897773709L;
    private int studentId;
    private int taskId;
    private String answer;
    private String review;
    private Integer mark;

    public TaskReview() {
    }

    public TaskReview(int studentId, Integer taskId, String taskAnswer, String teacherComment, Integer mark) {
        this.studentId = studentId;
        this.taskId = taskId;
        this.answer = taskAnswer;
        this.review = teacherComment;
        this.mark = mark;
    }

    public int getId() {
        return studentId;
    }

    public void setId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getReview() { return review; }

    public void setReview(String teacherComment) {
        this.review = teacherComment;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getAnswer() { return answer; }

    public void setAnswer(String taskAnswer) { this.answer = taskAnswer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskReview that = (TaskReview) o;
        return Objects.equals(studentId, that.studentId) 
                && Objects.equals(taskId, that.taskId) 
                && Objects.equals(answer, that.answer) 
                && Objects.equals(review, that.review) 
                && Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, taskId, answer, review, mark);
    }

    @Override
    public String toString() {
        return "TaskReview{" +
                "studentId=" + studentId +
                ", taskId=" + taskId +
                ", taskAnswer='" + answer + '\'' +
                ", teacherComment='" + review + '\'' +
                ", mark=" + mark +
                '}';
    }
}
