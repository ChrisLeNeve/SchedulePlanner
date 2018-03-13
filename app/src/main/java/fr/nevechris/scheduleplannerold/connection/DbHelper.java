package fr.nevechris.scheduleplannerold.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chris on 01/11/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "planner1.db";
    private static final String SQL_CREATE_EXAMS = "CREATE TABLE "
            + DatabaseContract.FeedEntry.EXAMS_TABLE_NAME + " ("
            + DatabaseContract.FeedEntry.EXAMS_EXAMID + " INTEGER,"
            + DatabaseContract.FeedEntry.EXAMS_TITLE + " TEXT,"
            + DatabaseContract.FeedEntry.EXAMS_DESCRIPTION + " TEXT,"
            + DatabaseContract.FeedEntry.EXAMS_DATE + " TEXT,"
            + DatabaseContract.FeedEntry.EXAMS_TEACHER_NAME + " TEXT,"
            + DatabaseContract.FeedEntry.EXAMS_PLACE + " TEXT,"
            + DatabaseContract.FeedEntry.EXAMS_SUBJECTID+ " INTEGER,"
            + DatabaseContract.FeedEntry.EXAMS_GRADE + " INTEGER,"
            + DatabaseContract.FeedEntry.EXAMS_DAYSNEEDEDTOPREPARE+ " INTEGER,"
            + DatabaseContract.FeedEntry.EXAMS_DIFFICULTY + " INTEGER,"
            + DatabaseContract.FeedEntry.EXAMS_NOTES + " TEXT)";
    private static final String SQL_CREATE_SUBJECTS = "CREATE TABLE "
            + DatabaseContract.FeedEntry.SUBJECTS_TABLE_NAME + " ("
            + DatabaseContract.FeedEntry.SUBJECTS_SUBJECTID + " INTEGER, "
            + DatabaseContract.FeedEntry.SUBJECTS_DESCRIPTION + " TEXT)";

    private static final String SQL_CREATE_LESSONS = "CREATE TABLE "
            + DatabaseContract.FeedEntry.LESSONS_TABLE_NAME + " ("
            + DatabaseContract.FeedEntry.LESSONS_ID + " INTEGER,"
            + DatabaseContract.FeedEntry.LESSONS_SUBJECTID + " INTEGER,"
            + DatabaseContract.FeedEntry.LESSONS_TITLE + " TEXT,"
            + DatabaseContract.FeedEntry.LESSONS_DESCRIPTION + " TEXT,"
            + DatabaseContract.FeedEntry.LESSONS_STUDIEDPERCENT + " INTEGER)";

    private static final String SQL_DELETE_EXAMS = "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.EXAMS_TABLE_NAME,
            SQL_DELETE_SUBJECTS = "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.SUBJECTS_TABLE_NAME,
            SQL_DELETE_LESSONS = "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.LESSONS_TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXAMS);
        db.execSQL(SQL_CREATE_SUBJECTS);
        db.execSQL(SQL_CREATE_LESSONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int nOldVersion, int iNewVersion) {
        db.execSQL(SQL_DELETE_EXAMS);
        db.execSQL(SQL_DELETE_SUBJECTS);
        db.execSQL(SQL_DELETE_LESSONS);
        onCreate(db);
    }

}
