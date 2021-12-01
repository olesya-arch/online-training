package epam.jwd.online_training.entity;

import java.util.Objects;

public class Course implements BaseEntity {

    private static final long serialVersionUID = -3456188089825198902L;
    private Integer id;
    private CourseType typeId;
    private int teacherId;
    private CourseStatus status;
    private Boolean isAvailable;
    private String title;
    private String description;

    public Course() {
    }

    public Course(Integer id, CourseType typeId, int teacherId, CourseStatus status, Boolean isAvailable,
                  String title, String description) {
        this.id = id;
        this.typeId = typeId;
        this.teacherId = teacherId;
        this.status = status;
        this.isAvailable = isAvailable;
        this.title = title;
        this.description = description;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CourseType getTypeId() {
        return typeId;
    }

    public void setTypeId(CourseType typeId) {
        this.typeId = typeId;
    }

    public int getTeacherId() {return teacherId;}

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
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

    public Boolean getAvailable() { return isAvailable; }

    public void setAvailable(Boolean available) { isAvailable = available; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id)
                && typeId == course.typeId
                && teacherId == course.teacherId
                && status == course.status
                && Objects.equals(isAvailable, course.isAvailable)
                && Objects.equals(title, course.title)
                && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeId, teacherId, status, isAvailable, title, description);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", isAvailable=" + isAvailable +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}