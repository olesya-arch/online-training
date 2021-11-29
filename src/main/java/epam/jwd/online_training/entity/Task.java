package epam.jwd.online_training.entity;

import java.util.Objects;

public class Task implements BaseEntity {

    private static final long serialVersionUID = -8805107403083345233L;
    private Integer id;
    private String title;
    private String description;
    private Course course;

    public Task () {
    }

    public Task(Integer id, String title, String description, Course course) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.course = course;
    }

    @Override
    public Integer getId() {
        return null;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(getId(), task.getId())
                && Objects.equals(getTitle(), task.getTitle())
                && Objects.equals(getCourse(), task.getCourse())
                && Objects.equals(getDescription(), task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getCourse());
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", course=" + course +
                '}';
    }
}
