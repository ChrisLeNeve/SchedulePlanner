package fr.nevechris.scheduleplanner.service;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.nevechris.scheduleplanner.connection.AppDatabase;
import fr.nevechris.scheduleplanner.entity.Exam;

/**
 * Created by Chris on 08/03/2018.
 */

public class ExamService {

    private AppDatabase db;

    public ExamService(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "scheduleplanner-db").build();
    }

    public List<Date> getAllExamDates() {
        List<Exam> exams = this.getAllExams();
        List<Date> examDates = new ArrayList<>();
        for (Exam e : exams)
            examDates.add(new Date(e.getStartDate()));

        return examDates;
    }

    public List<Exam> getAllExams() {
        return db.examDao().getExams();
    }

    public void createExam(Exam e) {
        db.examDao().createExam(e);
    }

    public boolean deleteAllExams() {
        try {
            db.examDao().deleteExams();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Exam getExamByDate(Date startDate) {
        return db.examDao().getExamByDate(startDate);
    }

}
