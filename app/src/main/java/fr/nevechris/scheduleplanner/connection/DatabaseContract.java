package fr.nevechris.scheduleplanner.connection;

import android.provider.BaseColumns;

/**
 * Created by Chris on 31/10/2017.
 * See:
 * https://developer.android.com/training/basics/data-storage/databases.html#PersistingDbConnection
 */

public class DatabaseContract {
    private DatabaseContract() {}

    public static class FeedEntry {
        public static final String EXAMS_TABLE_NAME = "EXAMS",
            EXAMS_EXAMID = "exam_id",
            EXAMS_TITLE = "title",
            EXAMS_DESCRIPTION = "description",
            EXAMS_DATE = "date_start",
            //EXAMS_ENDDATE = "date_end",
            EXAMS_TEACHER_NAME = "teacher_name",
            EXAMS_PLACE = "place",
            EXAMS_SUBJECTID = "subject_id",
            EXAMS_GRADE = "grade",
            EXAMS_DAYSNEEDEDTOPREPARE = "days_needed_to_prepare",
            EXAMS_DIFFICULTY = "difficulty",
            EXAMS_NOTES = "notes";

        public static final String SUBJECTS_TABLE_NAME = "SUBJECTS",
            SUBJECTS_SUBJECTID = "subject_id",
            SUBJECTS_DESCRIPTION = "description";

        public static final String LESSONS_TABLE_NAME = "LESSONS",
            LESSONS_ID = "lesson_id",
            LESSONS_SUBJECTID = "subject_id",
            LESSONS_TITLE = "title",
            LESSONS_DESCRIPTION = "description",
            LESSONS_STUDIEDPERCENT = "studied_percent";
    }
}
