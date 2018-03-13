package fr.nevechris.scheduleplannerold;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.nevechris.scheduleplannerold.beans.Examold;
import fr.nevechris.scheduleplannerold.connection.DatabaseContract;
import fr.nevechris.scheduleplannerold.connection.DatabaseManager;

import static fr.nevechris.scheduleplannerold.R.string.today_exam;

public class DateExamsold extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_exams);

        long dateAsLong = Long.valueOf(getIntent().getStringExtra("date"));
        Date selectedDate = new Date(dateAsLong);
        String sSelectedDateAsString = new SimpleDateFormat("dd MMM yy", Locale.getDefault()).format(selectedDate).toString(); // TODO is this localisation enough?

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.today_exam);
        String test = getString(today_exam, sSelectedDateAsString);
        toolbar.setTitle(test);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Examold exam = getExamForDayFromDatabase(dateAsLong);

        RelativeLayout noExamLayout = (RelativeLayout) findViewById(R.id.layout_no_exam),
                examLayout = (RelativeLayout) findViewById(R.id.layout_exam);
        if (exam != null) {
            noExamLayout.setVisibility(View.INVISIBLE);
            examLayout.setVisibility(View.VISIBLE);
            TextView titleTV = (TextView) findViewById(R.id.examTitleForDate),
                    descriptionTV = (TextView) findViewById(R.id.examDescription),
                    teacherNameTV = (TextView) findViewById(R.id.teacherName),
                    examPlaceTV = (TextView) findViewById(R.id.placeTextView),
                    examGradeTV = (TextView) findViewById(R.id.examGrade);
            titleTV.setText(exam.getDescription());
            descriptionTV.setText(exam.getDescription());
            teacherNameTV.setText(exam.getTeacherName());
            examPlaceTV.setText(exam.getPlace());
            examGradeTV.setText(String.valueOf(exam.getGrade()));
        } else {
            noExamLayout.setVisibility(View.VISIBLE);
            examLayout.setVisibility(View.INVISIBLE);
        }

    }


    private Examold getExamForDayFromDatabase(long dateAsLong) {
        SQLiteDatabase db = DatabaseManager.getConnection().getReadableDatabase();
        String[] projection = {
                DatabaseContract.FeedEntry.EXAMS_EXAMID,
                DatabaseContract.FeedEntry.EXAMS_TITLE,
                DatabaseContract.FeedEntry.EXAMS_DESCRIPTION,
                DatabaseContract.FeedEntry.EXAMS_TEACHER_NAME,
                DatabaseContract.FeedEntry.EXAMS_PLACE,
                DatabaseContract.FeedEntry.EXAMS_GRADE
        };

        // Where clause
        String sWhereClause = DatabaseContract.FeedEntry.EXAMS_DATE + " = ?";
        String[] sWhereArgs = { String.valueOf(dateAsLong) };

        Cursor cursor = db.query(DatabaseContract.FeedEntry.EXAMS_TABLE_NAME, projection, sWhereClause, sWhereArgs, null, null, null);
        Examold currentExam = null;
        while(cursor.moveToNext()) {
            int nExamId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_EXAMID));
            String sTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_TITLE)),
                    sDescription = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_DESCRIPTION)),
                    sTeacherName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_TEACHER_NAME)),
                    sPlace = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_PLACE));
            int nGrade = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_GRADE));

            currentExam = new Examold(nExamId, sTitle, sDescription, sTeacherName, sPlace);
            currentExam.setGrade(nGrade);// TODO organise constructors
        }

        return currentExam;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
