package epam.jwd.online_training.dto;

import java.io.Serializable;

public class StatisticDto implements Serializable {

    private int usersCount;
    private int tasksCount;
    private int coursesCount;
    private int teachersCount;
    private int courseTypesCount;
    private int englishLanguageCount;
    private int germanLanguageCount;
    private int frenchLanguageCount;
    private int latvianLanguageCount;
    private int chineseLanguageCount;

    public StatisticDto() {
    }

    public StatisticDto(int usersCount, int tasksCount, int coursesCount, int teachersCount, int courseTypesCount,
                        int englishLanguageCount, int germanLanguageCount, int frenchLanguageCount,
                        int latvianLanguageCount, int chineseLanguageCount) {
        this.usersCount = usersCount;
        this.tasksCount = tasksCount;
        this.coursesCount = coursesCount;
        this.teachersCount = teachersCount;
        this.courseTypesCount = courseTypesCount;
        this.englishLanguageCount = englishLanguageCount;
        this.germanLanguageCount = germanLanguageCount;
        this.frenchLanguageCount = frenchLanguageCount;
        this.latvianLanguageCount = latvianLanguageCount;
        this.chineseLanguageCount = chineseLanguageCount;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(int tasksCount) {
        this.tasksCount = tasksCount;
    }

    public int getCoursesCount() {
        return coursesCount;
    }

    public void setCoursesCount(int coursesCount) {
        this.coursesCount = coursesCount;
    }

    public int getTeachersCount() {
        return teachersCount;
    }

    public void setTeachersCount(int teachersCount) {
        this.teachersCount = teachersCount;
    }

    public int getCourseTypesCount() { return courseTypesCount; }

    public void setCourseTypesCount(int courseTypesCount) { this.courseTypesCount = courseTypesCount; }

    public int getEnglishLanguageCount() {
        return englishLanguageCount;
    }

    public void setEnglishLanguageCount(int englishLanguageCount) {
        this.englishLanguageCount = englishLanguageCount;
    }

    public int getGermanLanguageCount() {
        return germanLanguageCount;
    }

    public void setGermanLanguageCount(int germanLanguageCount) {
        this.germanLanguageCount = germanLanguageCount;
    }

    public int getFrenchLanguageCount() {
        return frenchLanguageCount;
    }

    public void setFrenchLanguageCount(int frenchLanguageCount) {
        this.frenchLanguageCount = frenchLanguageCount;
    }

    public int getLatvianLanguageCount() { return latvianLanguageCount; }

    public void setLatvianLanguageCount(int latvianLanguageCount) { this.latvianLanguageCount = latvianLanguageCount; }

    public int getChineseLanguageCount() { return chineseLanguageCount; }

    public void setChineseLanguageCount(int chineseLanguageCount) { this.chineseLanguageCount = chineseLanguageCount; }
}
