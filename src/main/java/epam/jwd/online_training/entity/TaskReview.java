package epam.jwd.online_training.entity;

import java.util.Objects;

public class TaskReview implements BaseEntity {

    private static final long serialVersionUID = -5658517227580711092L;
    private Integer studentId;
    private Integer taskId;
    private String taskAnswer;
    private String teacherComment;
    private Integer mark;

    public TaskReview() {
    }

    public TaskReview(Integer studentId, Integer taskId, String taskAnswer, String teacherComment, Integer mark) {
        this.studentId = studentId;
        this.taskId = taskId;
        this.taskAnswer = taskAnswer;
        this.teacherComment = teacherComment;
        this.mark = mark;
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

    public String getTeacherComment() { return teacherComment; }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getTaskAnswer() { return taskAnswer; }

    public void setTaskAnswer(String taskAnswer) { this.taskAnswer = taskAnswer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskReview that = (TaskReview) o;
        return Objects.equals(studentId, that.studentId) 
                && Objects.equals(taskId, that.taskId) 
                && Objects.equals(taskAnswer, that.taskAnswer) 
                && Objects.equals(teacherComment, that.teacherComment) 
                && Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, taskId, taskAnswer, teacherComment, mark);
    }

    @Override
    public String toString() {
        return "TaskReview{" +
                "studentId=" + studentId +
                ", taskId=" + taskId +
                ", taskAnswer='" + taskAnswer + '\'' +
                ", teacherComment='" + teacherComment + '\'' +
                ", mark=" + mark +
                '}';
    }
}
