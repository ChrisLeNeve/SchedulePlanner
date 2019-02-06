package fr.nevechris.scheduleplanner.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Chris on 01/11/2017.
 */

@Entity
public class Exam {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exam_id")
    private int id;

    private String title;

    private String description;

    @ColumnInfo(name = "date_start")
    private long startDate;

    @ColumnInfo(name = "teacher_name")
    private String teacherName;

    private String place;

    @ColumnInfo(name = "subject_description")
    private String subjectDescription;

    private int grade;

    @ColumnInfo(name = "days_needed_to_prepare")
    private int daysNeededToPrepare;

    private int difficulty;

    private String notes;

    @Ignore
    public Exam() {
    }

    @Ignore
    public Exam(int id, String title, String description, String teacherName, String place) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherName = teacherName;
        this.place = place;
    }

    @Ignore
    public Exam(String title, String description, long startDate, String teacherName, String place, int difficulty, String notes, String subjectDescription) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.teacherName = teacherName;
        this.place = place;
        this.difficulty = difficulty;
        this.notes = notes;
        this.subjectDescription = subjectDescription;
    }

    public Exam(String title, String description, long startDate, String teacherName, String place, String subjectDescription, int grade, int daysNeededToPrepare, int difficulty, String notes) {
        this(title, description, startDate, teacherName, place, difficulty, notes, subjectDescription);
        this.place = place;
        this.grade = grade;
        this.daysNeededToPrepare = daysNeededToPrepare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getDaysNeededToPrepare() {
        return daysNeededToPrepare;
    }

    public void setDaysNeededToPrepare(int daysNeededToPrepare) {
        this.daysNeededToPrepare = daysNeededToPrepare;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
