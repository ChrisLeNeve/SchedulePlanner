package fr.nevechris.scheduleplanner.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.nevechris.scheduleplanner.entity.Exam;
import fr.nevechris.scheduleplanner.service.ExamService;
import fr.nevechris.scheduleplannerold.R;

public class CreateExamActivity extends AppCompatActivity {

    private EditText examDate;
    private EditText subjectEditText;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText teacherNameEditText;
    private EditText difficultyEditText;
    private EditText placeEditText;
    private EditText notesEditText;
    private EditText gradeEditText;
    private EditText datePicker;
    private DatePickerDialog datePickerDialog;
    private Button saveButton;
    private SimpleDateFormat dateFormatter;

    private ExamService examService;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        Toolbar toolbar = (Toolbar) findViewById(R.id.createExam_toolbar);
        toolbar.setTitle("Create exam");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.context = this.getApplicationContext();
        examService = new ExamService(getApplicationContext());

        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.ITALIAN);

        subjectEditText = (EditText) findViewById(R.id.createExam_Subject_EditText);
        titleEditText = (EditText) findViewById(R.id.createExam_Title_EditText);
        descriptionEditText = (EditText) findViewById(R.id.createExam_Description_EditText);
        teacherNameEditText = (EditText) findViewById(R.id.createExam_TeacherName_EditText);
        difficultyEditText = (EditText) findViewById(R.id.createExam_Difficulty_EditText);
        placeEditText = (EditText) findViewById(R.id.createExam_Place_EditText);
        notesEditText = (EditText) findViewById(R.id.createExam_Notes_EditText);
        gradeEditText = (EditText) findViewById(R.id.createExam_Grade_EditText);
        datePicker = (EditText) findViewById(R.id.createExam_Date_EditText);
        examDate = (EditText) findViewById(R.id.createExam_Date_EditText);

        examDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bHasFocus) {
                if (bHasFocus)
                    datePickerDialog.show();
            }
        });
        initialiseDatePicker();

        saveButton = (Button) findViewById(R.id.createExam_Save_Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    persistExam(view);
                } catch (ParseException e) {
                    System.out.println("Ah! dose.");
                }
            }
        });
    }

    private void persistExam(View view) throws ParseException {
        if (allRequiredFieldsArePresent()) {
            // TODO get the constructor work together

            Exam newExam = new Exam(
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    dateFormatter.parse(datePicker.getText().toString()).getTime(),
                    teacherNameEditText.getText().toString(),
                    placeEditText.getText().toString(),
                    (difficultyEditText.getText().toString().equalsIgnoreCase("") ? 0 : Integer.valueOf(difficultyEditText.getText().toString())),
                    notesEditText.getText().toString(),
                    subjectEditText.getText().toString()
            );
            String gradeAsString = gradeEditText.getText().toString();
            if (!gradeAsString.equals(""))
                newExam.setGrade(Integer.parseInt(gradeAsString));

            saveExam(newExam);
        }
    }

    private void goBackToHomeScreen() {
        Intent homeScreenActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeScreenActivity);
        //TODO: refresh calendar data on home screen and display toast ("Examold created !")
    }

    private void initialiseDatePicker() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                examDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void saveExam(final Exam exam) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                examService.createExam(exam);
                return null;//What in the world!
            }
            @Override
            protected void onPostExecute(Void whatInTheWorld) {
                goBackToHomeScreen();
            }
        }.execute();

//        SQLiteDatabase writeDb = DatabaseManager.getConnection().getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DatabaseContract.FeedEntry.EXAMS_EXAMID, 1);
//        values.put(DatabaseContract.FeedEntry.EXAMS_DATE, exam.getStartDate());
//        values.put(DatabaseContract.FeedEntry.EXAMS_TITLE, exam.getTitle());
//        values.put(DatabaseContract.FeedEntry.EXAMS_DAYSNEEDEDTOPREPARE, exam.getDaysNeededToPrepare());
//        values.put(DatabaseContract.FeedEntry.EXAMS_DESCRIPTION, exam.getDescription());
//        values.put(DatabaseContract.FeedEntry.EXAMS_DIFFICULTY, exam.getDifficulty());
//        values.put(DatabaseContract.FeedEntry.EXAMS_PLACE, exam.getPlace());
//        values.put(DatabaseContract.FeedEntry.EXAMS_TEACHER_NAME, exam.getTeacherName());
//        values.put(DatabaseContract.FeedEntry.EXAMS_GRADE, exam.getGrade());
//        long newRowId = writeDb.insert(DatabaseContract.FeedEntry.EXAMS_TABLE_NAME, null, values);
    }

    private boolean allRequiredFieldsArePresent() {
        boolean bAreWeGoodToGo = true;

        if(subjectEditText.getText().toString().equalsIgnoreCase("")) {
            subjectEditText.setError("Please enter a subject.");
            bAreWeGoodToGo = false;
        } else subjectEditText.setError(null);

        if(titleEditText.getText().toString().equalsIgnoreCase("")) {
            titleEditText.setError("Please enter a description");
            bAreWeGoodToGo = false;
        } else titleEditText.setError(null);

        if(placeEditText.getText().toString().equalsIgnoreCase("")) {
            placeEditText.setError("Please enter a place");
            bAreWeGoodToGo = false;
        } else placeEditText.setError(null);

        try {
            Date d = dateFormatter.parse(datePicker.getText().toString());
            datePicker.setError(null);
        } catch (ParseException e) {
            datePicker.setError("Please pick a valid date.");
            bAreWeGoodToGo = false;
        }

        return bAreWeGoodToGo;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
