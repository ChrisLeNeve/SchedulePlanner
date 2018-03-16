package fr.nevechris.scheduleplanner.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import fr.nevechris.scheduleplanner.converter.DateConverter;
import fr.nevechris.scheduleplanner.entity.Exam;

/**
 * Created by Chris on 07/03/2018.
 */

@Dao
public interface ExamDao {
    @Query("SELECT * FROM EXAM")
    List<Exam> getExams();

    @Insert
    void createExam(Exam exam);

    @Delete
    void deleteExam(Exam exam);

    @Query("DELETE FROM EXAM WHERE 1=1")
    void deleteExams();

    @Query("SELECT * FROM EXAM WHERE date_start = :startDate")
    @TypeConverters(DateConverter.class)
    Exam getExamByDate(Date startDate);

    @Query("SELECT * FROM EXAM WHERE grade != 0")
    List<Exam> getExamsWithGrades();
}
