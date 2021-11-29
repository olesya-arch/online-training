package epam.jwd.online_training.entity;

import java.util.Objects;

public class TaskPerformed implements BaseEntity {

    private static final long serialVersionUID = -8613916273390929974L;
    private Integer id;
    private Task task;
    private User student;
    private String teacherComment;
    private Integer mark;

    public TaskPerformed() {
    }

    public TaskPerformed(Integer id, Task task, User student, String teacherComment, Integer mark) {
        this.id = id;
        this.task = task;
        this.student = student;
        this.teacherComment = teacherComment;
        this.mark = mark;
    }

    @Override
    public Integer getId() {
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
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
        return Objects.equals(id, that.id)
                && Objects.equals(task, that.task)
                && Objects.equals(student, that.student)
                && Objects.equals(teacherComment, that.teacherComment)
                && Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, student, teacherComment, mark);
    }

    @Override
    public String toString() {
        return "TaskPerformed{" +
                "id=" + id +
                ", task=" + task +
                ", student=" + student +
                ", teacherComment='" + teacherComment + '\'' +
                ", mark=" + mark +
                '}';
    }
}
