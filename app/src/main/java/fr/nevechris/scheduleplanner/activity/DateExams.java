package fr.nevechris.scheduleplanner.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.nevechris.scheduleplanner.entity.Exam;
import fr.nevechris.scheduleplanner.service.ExamService;
import fr.nevechris.scheduleplanner.R;

import static fr.nevechris.scheduleplanner.R.string.today_exam;

public class DateExams extends AppCompatActivity {

    private ExamService examService;

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


        examService = new ExamService(getApplicationContext());
        
        getExamThenUpdateUI(dateAsLong);
    }
    
    private void updateUIWithExamData(Exam e) {
        RelativeLayout noExamLayout = (RelativeLayout) findViewById(R.id.layout_no_exam),
                examLayout = (RelativeLayout) findViewById(R.id.layout_exam);
        if (e != null) {
            noExamLayout.setVisibility(View.INVISIBLE);
            examLayout.setVisibility(View.VISIBLE);
            TextView titleTV = (TextView) findViewById(R.id.examTitleForDate),
                    descriptionTV = (TextView) findViewById(R.id.examDescription),
                    subjectTV = (TextView) findViewById(R.id.examSubject),
                    teacherNameTV = (TextView) findViewById(R.id.teacherName),
                    examPlaceTV = (TextView) findViewById(R.id.placeTextView),
                    difficultyTV = (TextView) findViewById(R.id.difficultyTextView),
                    notesTV =(TextView) findViewById(R.id.notesTextView),
                    examGradeTV = (TextView) findViewById(R.id.examGrade);
            titleTV.setText(e.getTitle());
            descriptionTV.setText(e.getDescription());
            subjectTV.setText(e.getSubjectDescription());
            teacherNameTV.setText(e.getTeacherName());
            examPlaceTV.setText(e.getPlace());
            difficultyTV.setText(String.valueOf(e.getDifficulty()));
            notesTV.setText(e.getNotes());
            examGradeTV.setText(String.valueOf(e.getGrade()));
        } else {
            noExamLayout.setVisibility(View.VISIBLE);
            examLayout.setVisibility(View.INVISIBLE);
        }
    }


    private void getExamThenUpdateUI(final long dateAsLong) {
        new AsyncTask<Void, Void, Exam>() {
            @Override
            protected Exam doInBackground(Void... params) {
                return examService.getExamByDate(new Date(dateAsLong));
            }
            @Override
            protected void onPostExecute(Exam exam) {
                updateUIWithExamData(exam);
            }
        }.execute();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
