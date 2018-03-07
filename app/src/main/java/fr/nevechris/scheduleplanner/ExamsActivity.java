package fr.nevechris.scheduleplanner;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.nevechris.scheduleplanner.beans.Exam;
import fr.nevechris.scheduleplanner.connection.DatabaseContract;
import fr.nevechris.scheduleplanner.connection.DatabaseManager;

public class ExamsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menu_past_exams));
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view_exams);
        navigationView.getMenu().getItem(1).setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.examsLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        DatabaseManager.createDbHelper(getApplication());
        List<Exam> allExamsWithGrades = fetchExamsWithResults();
        displayInTable(allExamsWithGrades);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_exams);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void displayInTable(List<Exam> allExamWithDates) {
        TableLayout table = (TableLayout) findViewById(R.id.contentExamsTable);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        for (int i = 0; i < allExamWithDates.size(); i++) {
            TableRow row = new TableRow(this);

            TextView textDescription = new TextView(this), textGrade = new TextView(this);
            textDescription.setText(allExamWithDates.get(i).getDescription());
            textDescription.setLayoutParams(params);
            textGrade.setText(String.valueOf(allExamWithDates.get(i).getGrade()));
            textGrade.setLayoutParams(params);

            row.addView(textDescription);
            row.addView(textGrade);

            table.addView(row);
        }
    }

    private List<Exam> fetchExamsWithResults() {
        SQLiteDatabase readDb = DatabaseManager.getConnection().getReadableDatabase();
        String[] projection = {
                DatabaseContract.FeedEntry.EXAMS_TITLE,
                DatabaseContract.FeedEntry.EXAMS_DESCRIPTION,
                DatabaseContract.FeedEntry.EXAMS_GRADE
        };

        // Where clause
        String sWhereClause = DatabaseContract.FeedEntry.EXAMS_GRADE + " <> ?";
        String[] sWhereArgs = { String.valueOf(0) };
//        Cursor cursor = readDb.query(DatabaseContract.FeedEntry.EXAMS_TABLE_NAME, projection, sWhereClause, /*sWhereArgs*/null, null, null, null);

        Cursor cursor = readDb.query(DatabaseContract.FeedEntry.EXAMS_TABLE_NAME, projection, sWhereClause, sWhereArgs, null, null, null);
        List<Exam> allExams = new ArrayList<Exam>();
        Date currentDate = null;
        while(cursor.moveToNext()) {
            String examTitle = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_TITLE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_DESCRIPTION));
            int examGrade = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FeedEntry.EXAMS_GRADE));

            Exam exam = new Exam();
            exam.setTitle(examTitle);
            exam.setDescription(description);
            exam.setGrade(examGrade);

            allExams.add(exam);
        }
        cursor.close();

        return allExams;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
//        } else if (id == R.id.nav_subjects) { //View outdated
//            Intent subjectsActivity = new Intent(getApplicationContext(), SubjectsActivity.class);
//            startActivity(subjectsActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.examsLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
