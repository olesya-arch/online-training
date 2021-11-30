package epam.jwd.online_training.dto;

import epam.jwd.online_training.entity.Course;
import epam.jwd.online_training.entity.CourseStatus;
import epam.jwd.online_training.entity.CourseType;
import epam.jwd.online_training.entity.User;

import java.io.Serializable;

public class CourseDto implements Serializable {

    private static final long serialVersionUID = 2729250273490344917L;
    private Integer id;
    private CourseType typeId;
    private CourseStatus status;
    private Boolean isAvailable;
    private String title;
    private String description;
    private User teacher;

    public CourseDto() {
    }

    public CourseDto(Course course, User teacher) {
        this.id = course.getId();
        this.typeId = course.getTypeId();
        this.status = course.getStatus();
        this.isAvailable = course.getAvailable();
        this.title = course.getTitle();;
        this.description = course.getDescription();
        this.teacher = teacher;
    }

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

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
