package epam.jwd.online_training.dto;

import epam.jwd.online_training.entity.*;

import java.io.Serializable;

public class CourseDto implements Serializable {

    private static final long serialVersionUID = 6406284031210356867L;
    private Integer id;
    private String title;
    private String description;
    private CourseType typeId;
    private User teacher;
    private CourseStatus status;
    private Boolean isAvailable;

    public CourseDto() {
    }

    public CourseDto(Course course, CourseType courseType, User teacher) {
        this.id = course.getId();
        this.title = course.getTitle();;
        this.description = course.getDescription();
        this.typeId = courseType;
        this.teacher = teacher;
        this.status = course.getStatus();
        this.isAvailable = course.getAvailable();
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
