package epam.jwd.online_training.entity;

import java.util.Objects;

public class CourseType implements BaseEntity {

    private static final long serialVersionUID = 9080519141328153391L;
    private int id;
    private Language language;

    public CourseType() {
    }

    public CourseType(int id, Language language) {
        this.id = id;
        this.language = language;
    }

    @Override
    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseType that = (CourseType) o;
        return id == that.id && language == that.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language);
    }

    @Override
    public String toString() {
        return "CourseType{" +
                "id=" + id +
                ", language=" + language +
                '}';
    }
}
