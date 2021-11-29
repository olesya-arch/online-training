package epam.jwd.online_training.entity;

import java.util.Objects;

public class TaskPerformed implements BaseEntity {

    private static final long serialVersionUID = -5960126299095018163L;
    private Integer studentId;
    private Integer taskId;
    private String teacherComment;
    private Integer mark;

    public TaskPerformed() {
    }

    public TaskPerformed(Integer studentId, Integer taskId, String teacherComment, Integer mark) {
        this.studentId = studentId;
        this.taskId = taskId;
        this.teacherComment = teacherComment;
        this.mark = mark;
    }

    public Integer getId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
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

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskPerformed that = (TaskPerformed) o;
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(taskId, that.taskId)
                && Objects.equals(teacherComment, that.teacherComment)
                && Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, taskId, teacherComment, mark);
    }

    @Override
    public String toString() {
        return "TaskPerformed{" +
                "studentId=" + studentId +
                ", taskId=" + taskId +
                ", teacherComment='" + teacherComment + '\'' +
                ", mark=" + mark +
                '}';
    }
}
