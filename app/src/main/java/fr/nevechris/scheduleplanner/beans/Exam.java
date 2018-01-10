package fr.nevechris.scheduleplanner.beans;

/**
 * Created by Chris on 01/11/2017.
 */

public class Exam {
    private int nExamId;
    private String sTitle;
    private String sDescription;
    private long lDate;
    private String sTeacherName;
    private String sPlace;
    private int nSubjectId;
    private int nGrade;
    private int nDaysNeededToPrepare;
    private int nDifficulty;
    private String sNotes;

    public Exam(int nExamId, long lDate) {
        this.nExamId = nExamId;
        this.lDate = lDate;
    }

    public Exam(int nExamId, String sTitle, String sDescription, String sTeacherName, String sPlace) {
        this.nExamId = nExamId;
        this.sTitle = sTitle;
        this.sDescription = sDescription;
        this.sTeacherName = sTeacherName;
        this.sPlace = sPlace;
    }

    public Exam(int nExamId, String sTitle, String sDescription, long lDate, String sTeacherName, String sPlace, int nSubjectId, int nGrade, int nDaysNeededToPrepare, int nDifficulty, String sNotes) {
        this.nExamId = nExamId;
        this.sTitle = sTitle;
        this.sDescription = sDescription;
        this.lDate = lDate;
        this.sTeacherName = sTeacherName;
        this.sPlace = sPlace;
        this.nSubjectId = nSubjectId;
        this.nGrade = nGrade;
        this.nDaysNeededToPrepare = nDaysNeededToPrepare;
        this.nDifficulty = nDifficulty;
        this.sNotes = sNotes;
    }

    public String getDescription() {
        return sDescription;
    }

    public void setDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public int getExamId() {
        return nExamId;
    }

    public void setExamId(int nExamId) {
        this.nExamId = nExamId;
    }

    public String getTitle() {
        return sTitle;
    }

    public void setTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public long getStartDate() {
        return lDate;
    }

    public void setStartDate(long lStartDate) {
        this.lDate = lStartDate;
    }

    public String getTeacherName() {
        return sTeacherName;
    }

    public void setTeacherName(String sTeacherName) {
        this.sTeacherName = sTeacherName;
    }

    public String getPlace() {
        return sPlace;
    }

    public void setPlace(String sPlace) {
        this.sPlace = sPlace;
    }

    public int getSubjectId() {
        return nSubjectId;
    }

    public void setSubjectId(int nSubjectId) {
        this.nSubjectId = nSubjectId;
    }

    public int getGrade() {
        return nGrade;
    }

    public void setGrade(int nGrade) {
        this.nGrade = nGrade;
    }

    public int getDaysNeededToPrepare() {
        return nDaysNeededToPrepare;
    }

    public void setDaysNeededToPrepare(int nDaysNeededToPrepare) {
        this.nDaysNeededToPrepare = nDaysNeededToPrepare;
    }

    public int getDifficulty() {
        return nDifficulty;
    }

    public void setDifficulty(int nDifficulty) {
        this.nDifficulty = nDifficulty;
    }

    public String getNotes() {
        return sNotes;
    }

    public void setNotes(String sNotes) {
        this.sNotes = sNotes;
    }
}
