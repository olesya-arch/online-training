package epam.jwd.online_training.entity;

import java.util.Objects;

public class Course implements BaseEntity {

    private static final long serialVersionUID = -3128697086708998985L;
    private int id;
    private String title;
    private String description;
    private int typeId;
    private int teacherId;
    private CourseStatus status;
    private boolean isAvailable;

    public Course() {
    }

    public Course(int id, String title, String description, int typeId,
                  int teacherId, CourseStatus status, Boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.typeId = typeId;
        this.teacherId = teacherId;
        this.status = status;
        this.isAvailable = isAvailable;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
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
        return teacherId == course.teacherId
                && isAvailable == course.isAvailable
                && Objects.equals(id, course.id)
                && Objects.equals(title, course.title)
                && Objects.equals(description, course.description)
                && typeId == course.typeId
                && status == course.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, typeId, teacherId, status, isAvailable);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", typeId=" + typeId +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
